package com.food.zotatoFoods.services.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;
import com.food.zotatoFoods.entites.MenuItem;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.CartItemRepository;
import com.food.zotatoFoods.services.CartItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Item not Found with CartItemId " + cartItemId));
    }

    @Override
    public CartItem createNewCartItem(MenuItem menuItem, Cart cart) {
        CartItem cartItem = CartItem.builder()
                .menuItem(menuItem)
                .cart(cart)
                .quantity(1)
                .totalPrice(menuItem.getPrice())
                .build();

        // The cart is responsible for persisting the CartItem due to cascade settings
        cart.getCartItems().add(cartItem);
        cart.setTotalPrice(cart.getTotalPrice().add(menuItem.getPrice()));
        return cartItem;
    }

    @Override
    public void incrementCartItemQuantity(Integer quantity, CartItem cartItem) {
        if (quantity == 0 || quantity < 0) {
            throw new RuntimeException("Quantity has to be greater than zero");
        }
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setTotalPrice(cartItem.getMenuItem().getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
    }

    @Override
    public void decrementCartItemQuantity(Integer quantity, CartItem cartItem) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (cartItem.getQuantity() < quantity) {
            throw new IllegalArgumentException("Quantity to decrement exceeds current quantity.");
        }

        cartItem.setQuantity(cartItem.getQuantity() - quantity);
        cartItem.setTotalPrice(cartItem.getMenuItem().getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
        cartItem.getCart().setTotalPrice(cartItem.getCart().getTotalPrice()
                .subtract(cartItem.getMenuItem().getPrice().multiply(new BigDecimal(quantity))));

    }

    @Override
    public void removeCartItemFromCart(CartItem cartItem) {
        Cart cart = cartItem.getCart();
        cart.getCartItems().remove(cartItem);
        cart.setTotalPrice(cart.getTotalPrice().subtract(cartItem.getTotalPrice()));
    }

    @Override
    public Boolean isCartItemExist(CartItem cartItem) {
        return cartItemRepository.existsById(cartItem.getId());
    }

    @Override
    public CartItem getCartItemByMenuItemAndCart(MenuItem menuItem, Cart cart) {
        return cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getMenuItem().getId().equals(menuItem.getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found for given menu item in cart."));
    }

    @Override
    public List<CartItem> getAllCartItemsByCartId(Long cartId) {
        return cartItemRepository.findAllByCartId(cartId);

    }

    @Override
    public boolean isMenuItemExistInCart(MenuItem menuItem, Cart cart) {
        return cartItemRepository.findByMenuItemIdAndCartId(menuItem.getId(), cart.getId()).isPresent();
    }

}
