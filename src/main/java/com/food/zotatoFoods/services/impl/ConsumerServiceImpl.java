package com.food.zotatoFoods.services.impl;

import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.CartDto;
import com.food.zotatoFoods.dto.ConsumerOTP;
import com.food.zotatoFoods.dto.CreateOrderRequest;
import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.dto.PreOrderRequestDto;
import com.food.zotatoFoods.dto.PrePaidOrderRequestsDto;
import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;
import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.DeliveryRequest;
import com.food.zotatoFoods.entites.Menu;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.enums.PaymentMethod;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.ConsumerRepository;
import com.food.zotatoFoods.services.CartItemService;
import com.food.zotatoFoods.services.CartService;
import com.food.zotatoFoods.services.ConsumerService;
import com.food.zotatoFoods.services.DeliveryService;
import com.food.zotatoFoods.services.MenuService;
import com.food.zotatoFoods.services.OrderRequestService;
import com.food.zotatoFoods.services.PreOrderRequestService;
import com.food.zotatoFoods.services.RestaurantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;
    private final OrderRequestService orderRequestService;
    private final RestaurantService restaurantService;
    private final ModelMapper modelMapper;
    private final MenuService menuService;
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final PreOrderRequestService preOrderRequestService;
    private final DeliveryService deliveryService;

    Double PLATFORM_COMMISSION = 10.5;

    @Override
    public Boolean rateRestaurant(Long RestaurantId, Double rating) {
        Restaurant restaurant = restaurantService.getRestaurantById(RestaurantId);
        restaurant.setRating(rating);
        restaurantService.save(restaurant);
        return true;
    }

    @Override
    public Consumer createNewConsumer(User user) {
        Consumer consumer = Consumer.builder().user(user).rating(0.0).build();
        return consumerRepository.save(consumer);
    }

    @Override
    public Consumer getConsumerById(Long consumerId) {
        return consumerRepository.findById(consumerId)
                .orElseThrow(() -> new ResourceNotFoundException("Consumer not found with consumer id =" + consumerId));
    }

    @Override
    public OrderRequestsDto createOrderRequest(Long RestaurantId, CreateOrderRequest createOrderRequest) {
        PaymentMethod paymentMethod = createOrderRequest.getPaymentMethod();
        Point UserLocation = createOrderRequest.getUserLocation();
        Consumer consumer = getCurrentConsumer();
        Cart cart = cartService.getCartByConsumerIdAndRestaurantId(consumer.getId(), RestaurantId);
        OrderRequests orderRequests = orderRequestService.OrderRequest(cart.getId(), paymentMethod, UserLocation);
        return modelMapper.map(orderRequests, OrderRequestsDto.class);
    }

    @Override
    public Consumer getCurrentConsumer() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return consumerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consumer Not Found assosicated with user with id =" + user.getId()));
    }

    @Override
    public CartDto PrepareCart(Long RestaurantId, Long MenuItemId) {
        Consumer consumer = getCurrentConsumer();
        return cartService.prepareCart(consumer, RestaurantId, MenuItemId);
    }

    @Override
    public CartDto removeCartItem(Long CartId, Long cartItemId) {
        Consumer consumer = getCurrentConsumer();
        Cart cart = cartService.getCartById(CartId);
        cartService.isValidCartExist(consumer, cart.getRestaurant().getId());
        CartItem cartItem = cartItemService.getCartItemById(cartItemId);
        return cartService.removeItemFromCart(CartId, cartItem);
    }

    @Override
    public void clearCart(Long RestaurantId) {
        Consumer consumer = getCurrentConsumer();
        Cart cart = cartService.getCartByConsumerIdAndRestaurantId(consumer.getId(), RestaurantId);
        cartService.deleteAllCartItemByCartId(cart.getId());
    }

    @Override
    public Menu viewMenuByRestaurantId(Long RestaurantId) {
        return menuService.getMenuByRestaurant(RestaurantId);
    }

    @Override
    public Page<Restaurant> getAllRestaurant(PageRequest pageRequest) {
        return restaurantService.getAllVarifiedRestaurant(pageRequest);
    }

    @Override
    public CartDto viewCart(Long RestaurantId) {
        Consumer consumer = getCurrentConsumer();
        Cart cart = cartService.getCartByConsumerIdAndRestaurantId(consumer.getId(), RestaurantId);
        return cartService.viewCart(cart.getId());
    }

    @Override
    public PreOrderRequestDto viewPreOrderRequest(Long RestaurantId, Point UserLocation) {
        Consumer consumer = getCurrentConsumer();
        Cart cart = cartService.getCartByConsumerIdAndRestaurantId(consumer.getId(), RestaurantId);
        return preOrderRequestService.createPreOrderRequest(cart, UserLocation);
    }

    @Override
    public PrePaidOrderRequestsDto createPrePaidOrderRequest(Long RestaurantId, CreateOrderRequest createOrderRequest) {
        PaymentMethod paymentMethod = createOrderRequest.getPaymentMethod();
        Point UserLocation = createOrderRequest.getUserLocation();
        Consumer consumer = getCurrentConsumer();
        Cart cart = cartService.getCartByConsumerIdAndRestaurantId(consumer.getId(), RestaurantId);
        OrderRequests orderRequests = orderRequestService.prePaidOrderRequest(cart.getId(), paymentMethod,
                UserLocation);
        PrePaidOrderRequestsDto prePaidOrderRequestsDto = modelMapper.map(orderRequests, PrePaidOrderRequestsDto.class);
        return prePaidOrderRequestsDto;
    }

    @Override
    public Boolean PreProcessPayment() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'PreProcessPayment'");
    }

    @Override
    public ConsumerOTP getOtpByOrderId(Long OrderId) {
        DeliveryRequest deliveryRequest = deliveryService.getDeliveryRequestByOrderId(OrderId);
        return ConsumerOTP.builder().consumerOTP(deliveryRequest.getConsumerOtp()).build();

    }

}
