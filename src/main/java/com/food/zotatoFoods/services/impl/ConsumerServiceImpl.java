package com.food.zotatoFoods.services.impl;

import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.CreateOrderRequest;
import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;
import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.Menu;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.enums.PaymentMethod;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.ConsumerRepository;
import com.food.zotatoFoods.services.CartService;
import com.food.zotatoFoods.services.ConsumerService;
import com.food.zotatoFoods.services.MenuService;
import com.food.zotatoFoods.services.OrderRequestService;
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
    public OrderRequestsDto createOrderRequest(Long CartId, CreateOrderRequest createOrderRequest) {
        PaymentMethod paymentMethod = createOrderRequest.getPaymentMethod();
        Point UserLocation = createOrderRequest.getUserLocation();
        OrderRequests orderRequests = orderRequestService.OrderRequest(CartId, paymentMethod, UserLocation);
        return modelMapper.map(orderRequests, OrderRequestsDto.class);
    }

    @Override
    public Consumer getCurrentConsumer() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return consumerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Rider Not Found assosicated with user with id =" + user.getId()));
    }

    @Override
    public Cart PrepareCart(Long RestaurantId, Long MenuItemId) {
        Consumer consumer = getCurrentConsumer();
        return cartService.prepareCart(consumer, RestaurantId, MenuItemId);
    }

    @Override
    public Cart removeCartItem(Long CartId, CartItem cartItem) {
        Consumer consumer = getCurrentConsumer();
        Cart cart = cartService.getCartById(CartId);
        cartService.isValidCartExist(consumer, cart.getRestaurant().getId());
        return cartService.removeItemFromCart(CartId, cartItem);
    }

    @Override
    public void clearCart(Long CartId) {
        cartService.deleteAllCartItemByCartId(CartId);
    }

    @Override
    public Menu viewMenuByRestaurantId(Long RestaurantId) {
        return menuService.getMenuByRestaurant(RestaurantId);
    }

    @Override
    public Page<Restaurant> getAllRestaurant(PageRequest pageRequest) {
        return restaurantService.getAllVarifiedRestaurant(pageRequest);
    }

}
