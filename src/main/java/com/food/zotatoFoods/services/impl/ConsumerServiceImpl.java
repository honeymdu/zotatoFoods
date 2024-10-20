package com.food.zotatoFoods.services.impl;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.enums.OrderRequestStatus;
import com.food.zotatoFoods.entites.enums.PaymentMethod;
import com.food.zotatoFoods.repositories.ConsumerRepository;
import com.food.zotatoFoods.services.CartService;
import com.food.zotatoFoods.services.ConsumerService;
import com.food.zotatoFoods.services.OrderRequestService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;
    private final CartService cartService;
    private final OrderRequestService orderRequestService;
    Double PLATFORM_COMMISSION = 10.5;
    Double DELIVERY_COMMISSION = 15.5;

    @Override
    public OrderRequestsDto createOrderRequest(Long CartId, PaymentMethod paymentMethod) {
        // check cart is a valid cart
        cartService.isValidCart(CartId);
        Cart cart = cartService.getCartById(CartId);

        OrderRequests orderRequests = OrderRequests.builder()
                .cart(cart)
                .consumer(cart.getUser())
                .deliveryFee(DELIVERY_COMMISSION)
                .platformFee(PLATFORM_COMMISSION)
                .foodAmount(cart.getTotalPrice())
                .orderRequestStatus(OrderRequestStatus.PENDING)
                .restaurant(cart.getRestaurant())
                .paymentMethod(paymentMethod)
                .totalPrice(cart.getTotalPrice() + DELIVERY_COMMISSION + PLATFORM_COMMISSION).build();

        // Send Notification to Corresponding restaurant

        OrderRequestsDto orderRequestsDto = orderRequestService.save(orderRequests);

        return orderRequestsDto;
        
    }

    @Override
    public Restaurant rateRestaurant(Long RestaurantId, Integer rating) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rateRestaurant'");
    }

    @Override
    public Consumer createNewConsumer(User user) {
        Consumer consumer = Consumer.builder().user(user).rating(0.0).build();
        return consumerRepository.save(consumer);
    }

    @Override
    public Consumer getConsumerById(Long consumerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getConsumerById'");
    }

}
