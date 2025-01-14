package com.food.zotatoFoods.services;

import com.food.zotatoFoods.dto.CartItemDto;
import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;
import com.food.zotatoFoods.entites.MenuItem;

public interface CartItemService {

    CartItem getCartItemById(Long cartItemId);

    CartItem createNewCartItem(MenuItem menuItem, Cart cart);

    CartItemDto incrementCartItemQuantity(Integer quantity, CartItem cartItem);

    CartItemDto decrementCartItemQuantity(Integer quantity, CartItem cartItem);

    void removeCartItemFromCart(CartItem cartItem);

    Boolean isCartItemExist(MenuItem menuItem, Cart cart);
    
    CartItem getCartItemByMenuItemAndCart(MenuItem menuItem, Cart cart);
}
