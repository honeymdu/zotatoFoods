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
import com.food.zotatoFoods.services.OrderService;
import com.food.zotatoFoods.services.RestaurantService;
import com.food.zotatoFoods.strategies.DeliveryPartnerMatchingStrategy;
import com.food.zotatoFoods.strategies.DeliveryStrategyManager;

import jakarta.annotation.PostConstruct;
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
    private final PriorityBlockingQueue<OrderPriorityQueue> highPriorityOrderQueue = new PriorityBlockingQueue<>();
    private final PriorityBlockingQueue<RestaurantPriorityQueue> restaurantPriorityQueue = new PriorityBlockingQueue<>();
    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 2, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10), new ThreadPoolExecutor.CallerRunsPolicy());
    private final PriorityBlockingQueue<OrderPriorityQueue> waitingQueue = new PriorityBlockingQueue<>();

    @Scheduled(fixedRate = 180000)
    public void monitorThreadPool() throws InterruptedException, ExecutionException {
        int activeThreads = threadPoolExecutor.getActiveCount();
        int corePoolSize = threadPoolExecutor.getCorePoolSize();
        // int maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();

        System.out.println("Checking thread pool status...");

        if (activeThreads == 0) {
            System.out.println("No threads are currently running, triggering task again.");
            // Trigger the task to assign a delivery partner again
            AssignDeliveryPartner();
        } else {
            System.out.println("Active threads: " + activeThreads + " (Core pool size: " + corePoolSize + ")");
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
                    log.info("Thread {} (ID: {}) started processing order", currentThread.getName(),
                            currentThread.getId());
                    sendNotificationToDeliveryPartner();
                    log.info("Thread {} (ID: {}) finished processing order", currentThread.getName(),
                            currentThread.getId());
                } catch (InterruptedException | RejectedExecutionException e) {
                    Thread.currentThread().interrupt();
                    log.error("Order processing thread interrupted: " + e.getMessage());
                   // waitingQueue.offer(highPriorityOrderQueue.poll());
                    throw new RuntimeException();
                }
            });

        }

        log.info("Assign Delivery Partner function completed at: {}", System.currentTimeMillis());

    }

    private void sendNotificationToDeliveryPartner()
            throws InterruptedException {
        log.info("sendNotificationToDeliveryPartner function Called");
        OrderPriorityQueue orderPriorityQueue = highPriorityOrderQueue.poll();
        log.info("Order Polled from Order Priority Queue Order Id = " + orderPriorityQueue.getId());
        Order order = orderService.getOrderById(orderPriorityQueue.getId());
        Restaurant restaurant = restaurantService.getRestaurantById(order.getRestaurant().getId());
        log.info("Restaurant Id " + restaurant.getRating());
        DeliveryPartnerMatchingStrategy deliveryPartnerMatchingStrategy = deliveryStrategyManager
                .deliveryPartnerMatchingStrategy(restaurant.getRating());

        DeliveryFareGetDto deliveryFareGetDto = DeliveryFareGetDto.builder()
                .PickupLocation(orderService.getOrderById(orderPriorityQueue.getId()).getPickupLocation())
                .DropLocation(order.getDropoffLocation()).build();
        List<DeliveryPartner> deliveryPartner = deliveryPartnerMatchingStrategy
                .findMatchingDeliveryPartner(deliveryFareGetDto);
        if (deliveryPartner.isEmpty()) {
            waitingQueue.offer(orderPriorityQueue);
        }
        if (deliveryPartner.isEmpty()) {
            log.info("Delivery Partner Is Empty");
        }
        if (!deliveryPartner.isEmpty()) {
            DeliveryRequest deliveryRequest = createDeliveryRequest(order);
            log.info("Delivery Request Created = " + deliveryRequest);
            // TODO send notification to matching deliveryPartners
            // TODO send OTP notification to restaurant partner
            // TODO send OTP notification to Consumer
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
                        .restaurantOtp(generateRandomOtp()).build());

    }

    private void createOrderPriorityQueue() {
        log.info("Create Order Priority Queue function Called");
        if (restaurantPriorityQueue == null) {
            log.warn("Restaurant priority queue is empty, cannot create order priority queue.");
            return;
        }
        // log.info("Polled RestaurantPriorityQueue object: {}",
        // restaurantPriorityQueue.poll());
        RestaurantPriorityQueue restaurantPriorityQueueEntry = restaurantPriorityQueue.poll();
        Restaurant restaurant = restaurantPriorityQueueEntry.getRestaurant();
        if (restaurant == null)
            return;
        // List<Order> orders = restaurant.getOrders();
        List<Order> orders = restaurant.getOrders().stream()
                .filter(order -> OrderStatus.READY_FOR_PICKUP.equals(order.getOrderStatus())).toList();
        log.info("Polled Restaurant From Restaurant Priority Queue = " + restaurant.getName());
        log.info("Number of Orders = " + orders.size());
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
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::escalateOrders, 0, 5, TimeUnit.SECONDS);
    }

    private void escalateOrders() {
        while (!waitingQueue.isEmpty()) {
            OrderPriorityQueue order = waitingQueue.poll();
            if (order != null) {
                highPriorityOrderQueue.offer(order);
                System.out.println("Order escalated to high-priority queue: " + order.getId());
            }
        }
    }

    private String generateRandomOtp() {
        return String.format("%04d", new Random().nextInt(10000));
    }

    @Override
    public DeliveryRequest getDeliveryRequestByOrderId(Long orderId) {
        return deliveryRequestRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery Request Not found for Order Id" + orderId));
    }

}
