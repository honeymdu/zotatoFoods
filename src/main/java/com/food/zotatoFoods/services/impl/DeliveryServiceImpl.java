package com.food.zotatoFoods.services.impl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.DeliveryFareGetDto;
import com.food.zotatoFoods.dto.OrderPriorityQueue;
import com.food.zotatoFoods.dto.RestaurantPriorityQueue;
import com.food.zotatoFoods.entites.DeliveryPartner;
import com.food.zotatoFoods.entites.DeliveryRequest;
import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.enums.DeliveryRequestStatus;
import com.food.zotatoFoods.entites.enums.OrderStatus;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.DeliveryRequestRepository;
import com.food.zotatoFoods.services.DeliveryService;
import com.food.zotatoFoods.services.NotificationService;
import com.food.zotatoFoods.services.OrderService;
import com.food.zotatoFoods.services.RestaurantService;
import com.food.zotatoFoods.strategies.DeliveryPartnerMatchingStrategy;
import com.food.zotatoFoods.strategies.DeliveryStrategyManager;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryStrategyManager deliveryStrategyManager;
    private final RestaurantService restaurantService;
    private final DeliveryRequestRepository deliveryRequestRepository;
    private final OrderService orderService;
    private final NotificationService notificationService;

    private final PriorityBlockingQueue<OrderPriorityQueue> highPriorityOrderQueue = new PriorityBlockingQueue<>();
    private final PriorityBlockingQueue<RestaurantPriorityQueue> restaurantPriorityQueue = new PriorityBlockingQueue<>();
    private final PriorityBlockingQueue<OrderPriorityQueue> waitingQueue = new PriorityBlockingQueue<>();

    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            5, 10, 2, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            new ThreadPoolExecutor.CallerRunsPolicy());

    private ScheduledExecutorService escalationScheduler;

    @Scheduled(fixedRate = 180000)
    public void monitorThreadPool() throws InterruptedException, ExecutionException {
        int activeThreads = threadPoolExecutor.getActiveCount();
        int corePoolSize = threadPoolExecutor.getCorePoolSize();
        log.info("Checking thread pool status...");
        if (activeThreads == 0) {
            log.info("No threads are currently running, triggering task again.");
            AssignDeliveryPartner();
        } else {
            log.info("Active threads: {} (Core pool size: {})", activeThreads, corePoolSize);
        }
    }

    @Override
    public void AssignDeliveryPartner() throws InterruptedException, ExecutionException {
        log.info("Assign Delivery Partner function started at: {}", System.currentTimeMillis());

        List<Restaurant> restaurants = restaurantService.getAllVarifiedAndActiveRestaurant();
        log.info("Fetched {} verified and active restaurants", restaurants.size());
        createRestaurantPriorityQueue(restaurants);

        if (!restaurantPriorityQueue.isEmpty()) {
            createOrderPriorityQueue();
            log.info("Created order priority queue with {} orders", highPriorityOrderQueue.size());
        } else {
            log.warn("No restaurants with ready orders to assign.");
        }

        while (!highPriorityOrderQueue.isEmpty()) {
            log.info("Processing {} orders", highPriorityOrderQueue.size());
            threadPoolExecutor.submit(() -> {
                try {
                    Thread currentThread = Thread.currentThread();
                    log.info("Thread {} (ID: {}) started processing order",
                            currentThread.getName(), currentThread.getId());
                    sendNotificationToDeliveryPartner();
                    log.info("Thread {} (ID: {}) finished processing order",
                            currentThread.getName(), currentThread.getId());
                } catch (InterruptedException | RejectedExecutionException e) {
                    Thread.currentThread().interrupt();
                    log.error("Order processing thread interrupted: {}", e.getMessage());
                    throw new RuntimeException(e);
                }
            });
        }

        log.info("Assign Delivery Partner function completed at: {}", System.currentTimeMillis());
    }

    private void sendNotificationToDeliveryPartner() throws InterruptedException {
        log.info("sendNotificationToDeliveryPartner function Called");

        OrderPriorityQueue orderPriorityQueue = highPriorityOrderQueue.poll();
        if (orderPriorityQueue == null) {
            log.warn("highPriorityOrderQueue was empty when polled — skipping.");
            return;
        }

        log.info("Order Polled from Order Priority Queue Order Id = {}", orderPriorityQueue.getId());
        Order order = orderService.getOrderById(orderPriorityQueue.getId());
        Restaurant restaurant = restaurantService.getRestaurantById(order.getRestaurant().getId());
        log.info("Restaurant rating: {}", restaurant.getRating());

        DeliveryPartnerMatchingStrategy deliveryPartnerMatchingStrategy =
                deliveryStrategyManager.deliveryPartnerMatchingStrategy(restaurant.getRating());

        DeliveryFareGetDto deliveryFareGetDto = DeliveryFareGetDto.builder()
                .PickupLocation(order.getPickupLocation())
                .DropLocation(order.getDropoffLocation())
                .build();

        List<DeliveryPartner> deliveryPartners =
                deliveryPartnerMatchingStrategy.findMatchingDeliveryPartner(deliveryFareGetDto);

        if (deliveryPartners.isEmpty()) {
            log.info("No delivery partners available — moving order {} to waiting queue", orderPriorityQueue.getId());
            waitingQueue.offer(orderPriorityQueue);
        } else {
            DeliveryRequest deliveryRequest = createDeliveryRequest(order);
            log.info("Delivery Request Created = {}", deliveryRequest);

            notificationService.notifyDeliveryPartners(deliveryPartners, deliveryRequest);
            notificationService.notifyRestaurantOtp(deliveryRequest);
            notificationService.notifyConsumerOtp(deliveryRequest);
        }

        log.info("sendNotificationToDeliveryPartner function Ended");
    }

    @Override
    @Transactional
    public DeliveryRequest createDeliveryRequest(Order order) {
        order.setOrderStatus(OrderStatus.DELIVERY_REQUEST_CREATED);
        orderService.saveOrder(order);
        return deliveryRequestRepository.save(
                DeliveryRequest.builder()
                        .deliveryRequestStatus(DeliveryRequestStatus.PENDING)
                        .PickupLocation(order.getPickupLocation())
                        .DropLocation(order.getDropoffLocation())
                        .order(order)
                        .consumerOtp(generateRandomOtp())
                        .restaurantOtp(generateRandomOtp())
                        .build());
    }

    private void createOrderPriorityQueue() {
        log.info("Create Order Priority Queue function Called");

        RestaurantPriorityQueue restaurantPriorityQueueEntry = restaurantPriorityQueue.poll();
        if (restaurantPriorityQueueEntry == null) {
            log.warn("Restaurant priority queue was empty when polled.");
            return;
        }

        Restaurant restaurant = restaurantPriorityQueueEntry.getRestaurant();
        if (restaurant == null) return;

        List<Order> orders = restaurant.getOrders().stream()
                .filter(order -> OrderStatus.READY_FOR_PICKUP.equals(order.getOrderStatus()))
                .toList();

        log.info("Polled Restaurant From Restaurant Priority Queue = {}", restaurant.getName());
        log.info("Number of Orders = {}", orders.size());

        for (Order order : orders) {
            highPriorityOrderQueue.offer(new OrderPriorityQueue(
                    order.getId(), order.getPaymentMethod(),
                    restaurantPriorityQueueEntry.getPriority(), System.currentTimeMillis()));
        }
        log.info("Order Priority Queue has been created");
    }

    private void createRestaurantPriorityQueue(List<Restaurant> restaurants) {
        log.info("Create Restaurant Priority Queue function Called");
        restaurants.forEach(restaurant -> {
            long readyForPickupCount = restaurant.getOrders().stream()
                    .filter(order -> OrderStatus.READY_FOR_PICKUP.equals(order.getOrderStatus()))
                    .count();
            if (readyForPickupCount > 0) {
                restaurantPriorityQueue.offer(new RestaurantPriorityQueue(restaurant, (int) readyForPickupCount));
            }
        });
        log.info("Restaurant Priority Queue Created");
    }

    @PostConstruct
    public void startEscalationProcess() {
        escalationScheduler = Executors.newScheduledThreadPool(1);
        escalationScheduler.scheduleAtFixedRate(this::escalateOrders, 0, 5, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down DeliveryService thread pools...");
        if (escalationScheduler != null) {
            escalationScheduler.shutdown();
        }
        threadPoolExecutor.shutdown();
        try {
            if (!threadPoolExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
                threadPoolExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPoolExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("DeliveryService thread pools shut down.");
    }

    private void escalateOrders() {
        while (!waitingQueue.isEmpty()) {
            OrderPriorityQueue order = waitingQueue.poll();
            if (order != null) {
                highPriorityOrderQueue.offer(order);
                log.info("Order escalated to high-priority queue: {}", order.getId());
            }
        }
    }

    private String generateRandomOtp() {
        return String.format("%04d", new Random().nextInt(10000));
    }

    @Override
    public DeliveryRequest getDeliveryRequestByOrderId(Long orderId) {
        return deliveryRequestRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Delivery Request Not found for Order Id " + orderId));
    }
}
