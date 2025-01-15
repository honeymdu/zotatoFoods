package com.food.zotatoFoods.services.impl;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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
import com.food.zotatoFoods.repositories.DeliveryRequestRepository;
import com.food.zotatoFoods.services.DeliveryService;
import com.food.zotatoFoods.services.RestaurantService;
import com.food.zotatoFoods.strategies.DeliveryPartnerMatchingStrategy;
import com.food.zotatoFoods.strategies.DeliveryStrategyManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryStrategyManager deliveryStrategyManager;
    private final RestaurantService restaurantService;
    private final DeliveryRequestRepository deliveryRequestRepository;

    @Override
    public void AssignDeliveryPartner() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 10, 2, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10));
        Queue<RestaurantPriorityQueue> pq = new PriorityQueue<>();
        List<Restaurant> restaurants = restaurantService.getAllVarifiedAndActiveRestaurant();
        // Loop through each restaurant
        for (Restaurant restaurant : restaurants) {
            int readyForPickupCount = 0;

            for (Order order : restaurant.getOrders()) {
                if (order.getOrderStatus().equals(OrderStatus.READY_FOR_PICKUP)) {
                    readyForPickupCount++;
                }
            }

            pq.offer(new RestaurantPriorityQueue(restaurant, readyForPickupCount));
        }

        while (!pq.isEmpty()) {
            RestaurantPriorityQueue restaurantPriorityQueue = pq.poll();
            Future<RestaurantPriorityQueue> future = threadPoolExecutor.submit(() -> {
                try {
                    return sendNotificationToDeliveryPartner(restaurantPriorityQueue);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            });
            pq.offer(future.get());

        }
    }

    private RestaurantPriorityQueue sendNotificationToDeliveryPartner(RestaurantPriorityQueue restaurantPriorityQueue)
            throws InterruptedException {
        Restaurant restaurant = restaurantPriorityQueue.getRestaurant();
        List<Order> orders = restaurant.getOrders();
        Queue<OrderPriorityQueue> oq = new PriorityQueue<>();
        for (Order order : orders) {
            oq.offer(new OrderPriorityQueue(order, order.getPayment().getPaymentMethod()));
        }

        Double restaurantRating = restaurant.getRating();
        DeliveryPartnerMatchingStrategy deliveryPartnerMatchingStrategy = deliveryStrategyManager
                .deliveryPartnerMatchingStrategy(restaurantRating);
        OrderPriorityQueue orderPriorityQueue = oq.poll();
        DeliveryFareGetDto deliveryFareGetDto = DeliveryFareGetDto.builder()
                .PickupLocation(restaurant.getRestaurantLocation())
                .DropLocation(orderPriorityQueue.getOrder().getDropoffLocation()).build();
        List<DeliveryPartner> deliveryPartner = deliveryPartnerMatchingStrategy
                .findMatchingDeliveryPartner(deliveryFareGetDto);
        if (!deliveryPartner.isEmpty()) {
            DeliveryRequest deliveryRequest = createDeliveryRequest(
                    orderPriorityQueue.getOrder());
            System.out.println(deliveryRequest);
            // TODO send notification to matching deliveryPartners
            // TODO send OTP notification to restaurant partner
            // TODO send OTP notification to Consumer
        }

        return restaurantPriorityQueue;
    }

    @Override
    public DeliveryRequest createDeliveryRequest(Order order) {
        DeliveryRequest deliveryRequest = DeliveryRequest.builder().deliveryRequestStatus(DeliveryRequestStatus.PENDING)
                .PickupLocation(order.getPickupLocation()).DropLocation(order.getDropoffLocation())
                .consumerOtp(generateRandomOtp())
                .restaurantOtp(generateRandomOtp()).build();
        DeliveryRequest savedDeliveryRequest = deliveryRequestRepository.save(deliveryRequest);
        return savedDeliveryRequest;

    }

    private String generateRandomOtp() {
        Random random = new Random();
        int randomnumber = random.nextInt(10000);
        return String.format("%04d", randomnumber);
    }

}
