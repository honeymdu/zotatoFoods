package com.food.zotatoFoods.services;

import com.food.zotatoFoods.dto.CartDto;
import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;
import com.food.zotatoFoods.entites.Consumer;

public interface CartService {

    Cart createCart(Long restaurantId, Consumer consumer);

    CartDto addItemToCart(Long CartId, CartItem cartItem);

    CartDto viewCart(Long CartId);

    CartDto removeItemFromCart(Long CartId, CartItem cartItem);

    void isValidCart(Cart cart);

    Boolean isValidCartExist(Consumer consumer, Long RestaurantId);

    void inValidCart(Cart cart);

    Cart getCartById(Long CartId);

    Cart saveCart(Cart cart);

    Cart getCartByConsumerIdAndRestaurantId(Long ConsumerId, Long restaurantId);

    void deleteAllCartItemByCartId(Long cartId);

    CartDto prepareCart(Consumer consumer, Long RestaurantId, Long MenuItemId);

    Cart clearCartItemFromCart(Long CartId);

}
