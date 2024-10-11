package com.food.zotatoFoods.services;

import java.util.List;

import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;

public interface CartService {

    public Cart createCart(Long UserId, List<CartItem> cartItem);

    public Cart addItemToCart(Long CartId, CartItem cartItem);

    public Cart viewCart(Long CartId);

    public Cart removeItemFromCart(Long CartId, CartItem cartItem);

}
