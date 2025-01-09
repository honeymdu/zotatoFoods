package com.food.zotatoFoods.services;

import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;

public interface CartService {

    public Cart createCart(Long UserId, Long restaurantId);

    public Cart addItemToCart(Long CartId, CartItem cartItem);

    public Cart viewCart(Long CartId);

    public Cart removeItemFromCart(Long CartId, CartItem cartItem);

    public void isValidCart(Cart cart);

    public Boolean isValidCartExist(Long ConsumerId, Long RestaurantId);

    public void inValidCart(Cart cart);

    public Cart getCartById(Long CartId);

    public Cart saveCart(Cart cart);

    public Cart getCartByConsumerIdAndRestaurantId(Long id, Long restaurantId);

    public void deleteAllCartItemByCartId(Long cartId);

}
