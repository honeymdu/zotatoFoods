package com.food.zotatoFoods.services;

import com.food.zotatoFoods.dto.CartDto;
import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;
import com.food.zotatoFoods.entites.Consumer;

public interface CartService {

    public Cart createCart(Long restaurantId, Consumer consumer);

    public CartDto addItemToCart(Long CartId, CartItem cartItem);

    public CartDto viewCart(Long CartId);

    public CartDto removeItemFromCart(Long CartId, CartItem cartItem);

    public void isValidCart(Cart cart);

    public Boolean isValidCartExist(Consumer consumer, Long RestaurantId);

    public void inValidCart(Cart cart);

    public Cart getCartById(Long CartId);

    public Cart saveCart(Cart cart);

    public Cart getCartByConsumerIdAndRestaurantId(Long ConsumerId, Long restaurantId);

    public void deleteAllCartItemByCartId(Long cartId);

    public CartDto prepareCart(Consumer consumer, Long RestaurantId, Long MenuItemId);

    public Cart clearCartItemFromCart(Long CartId);

}
