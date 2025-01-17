package com.food.zotatoFoods.services.impl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryStrategyManager deliveryStrategyManager;
    private final RestaurantService restaurantService;
    private final DeliveryRequestRepository deliveryRequestRepository;
    private final OrderService orderService;
    private final PriorityBlockingQueue<OrderPriorityQueue> highPriorityOrderQueue = new PriorityBlockingQueue<>();
    private final PriorityBlockingQueue<RestaurantPriorityQueue> restaurantPriorityQueue = new PriorityBlockingQueue<>();
    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 10, 2, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10));
    private final PriorityBlockingQueue<OrderPriorityQueue> waitingQueue = new PriorityBlockingQueue<>();

    @Override
    public void AssignDeliveryPartner() throws InterruptedException, ExecutionException {

        List<Restaurant> restaurants = restaurantService.getAllVarifiedAndActiveRestaurant();

        createRestaurantPriorityQueue(restaurants);

        if (!restaurantPriorityQueue.isEmpty()) {
            createOrderPriorityQueue();
        }

        while (!highPriorityOrderQueue.isEmpty()) {

            threadPoolExecutor.submit(() -> {
                try {
                    sendNotificationToDeliveryPartner();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Order processing thread interrupted: " + e.getMessage());
                    throw new RuntimeException();
                }
            });

        }

    }

    private void sendNotificationToDeliveryPartner()
            throws InterruptedException {
        OrderPriorityQueue orderPriorityQueue = highPriorityOrderQueue.poll();
        Order order = orderService.getOrderById(orderPriorityQueue.getId());
        Restaurant restaurant = restaurantService.getRestaurantById(order.getRestaurant().getId());
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
        if (!deliveryPartner.isEmpty()) {
            DeliveryRequest deliveryRequest = createDeliveryRequest(order);
            System.out.println(deliveryRequest);
            // TODO send notification to matching deliveryPartners
            // TODO send OTP notification to restaurant partner
            // TODO send OTP notification to Consumer
        }

    }

    @Override
    public DeliveryRequest createDeliveryRequest(Order order) {
        return deliveryRequestRepository.save(
                DeliveryRequest.builder()
                        .deliveryRequestStatus(DeliveryRequestStatus.PENDING)
                        .PickupLocation(order.getPickupLocation())
                        .DropLocation(order.getDropoffLocation())
                        .consumerOtp(generateRandomOtp())
                        .restaurantOtp(generateRandomOtp()).build());

    }

    private void createOrderPriorityQueue() {
        Restaurant restaurant = restaurantPriorityQueue.poll().getRestaurant();
        if (restaurant == null)
            return;
        List<Order> orders = restaurant.getOrders();
        for (Order order : orders) {
            highPriorityOrderQueue.offer(new OrderPriorityQueue(
                    order.getId(), order.getPaymentMethod(),
                    restaurantPriorityQueue.poll().getPriority(), System.currentTimeMillis()));
        }
    }

    private void createRestaurantPriorityQueue(List<Restaurant> restaurants) {

        restaurants.forEach(restaurant -> {
            long readyForPickupCount = restaurant.getOrders().stream()
                    .filter(order -> OrderStatus.READY_FOR_PICKUP.equals(order.getOrderStatus()))
                    .count();

            if (readyForPickupCount > 0) {
                restaurantPriorityQueue.offer(new RestaurantPriorityQueue(restaurant, (int) readyForPickupCount));
            }
        });
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
