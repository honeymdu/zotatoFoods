package com.food.zotatoFoods.services;


import com.food.zotatoFoods.dto.CartDto;
import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;

public interface CartService {

    public CartDto createCart(Long UserId,Long restaurantId);

    public CartDto addItemToCart(Long CartId, CartItem cartItem);

    public CartDto viewCart(Long CartId);

    public CartDto removeItemFromCart(Long CartId, CartItem cartItem);

    public void isValidCart(Long CartId);

    public Cart getCartById(Long CartId);

    public Cart saveCart(Cart cart);

}
