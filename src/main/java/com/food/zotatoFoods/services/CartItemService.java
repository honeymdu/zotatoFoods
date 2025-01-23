package com.food.zotatoFoods.services;

import java.util.List;

import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;
import com.food.zotatoFoods.entites.MenuItem;

public interface CartItemService {

    CartItem getCartItemById(Long cartItemId);

    CartItem createNewCartItem(MenuItem menuItem, Cart cart);

    void incrementCartItemQuantity(Integer quantity, CartItem cartItem);

    void decrementCartItemQuantity(Integer quantity, CartItem cartItem);

    void removeCartItemFromCart(CartItem cartItem);

    Boolean isCartItemExist(CartItem cartItem);

    CartItem getCartItemByMenuItemAndCart(MenuItem menuItem, Cart cart);

    List<CartItem> getAllCartItemsByCartId(Long cartId);

    boolean isMenuItemExistInCart(MenuItem menuItem, Cart cart);


}
