package com.food.zotatoFoods.services;

import org.locationtech.jts.geom.Point;

import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;
import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.MenuItem;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.enums.PaymentMethod;

public interface ConsumerService {

    public OrderRequestsDto createOrderRequest(Cart Cart, PaymentMethod paymentMethod, Point UserLocation);

    public Boolean rateRestaurant(Long RestaurantId, Double rating);

    public Consumer createNewConsumer(User user);

    public Consumer getConsumerById(Long consumerId);

    public Boolean cancelOrderRequest(Long OrderRequestId);

    public Consumer getCurrentConsumer();

    public Cart PrepareCart(Long RestaurantId, MenuItem menuItem);

    public Cart removeCartItemFromCart(Long CartId, CartItem cartItem);

    public void clearCart(Long CartId);

}
