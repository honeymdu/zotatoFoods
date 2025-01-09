package com.food.zotatoFoods.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.CartItemDto;
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
    private final ModelMapper modelMapper;

    @Override
    public CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Item not Found with CartItemId " + cartItemId));
    }

    @Override
    public CartItem createNewCartItem(MenuItem menuItem, Cart cart) {
        cart.setTotalPrice(menuItem.getPrice() + cart.getTotalPrice());
        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .menuItem(menuItem)
                .quantity(1)
                .totalPrice(menuItem.getPrice())
                .build();
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItemDto incrementCartItemQuantity(Integer quantity, CartItem cartItem) {
        if (quantity == 0 || quantity < 0) {
            throw new RuntimeException("Quantity has to be greater than zero");
        }
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        Cart cart = cartItem.getCart();
        cart.setTotalPrice(cart.getTotalPrice() + cartItem.getMenuItem().getPrice() * quantity);
        cartItem.setCart(cart);
        return modelMapper.map(cartItemRepository.save(cartItem), CartItemDto.class);
    }

    @Override
    public CartItemDto decrementCartItemQuantity(Integer quantity, CartItem cartItem) {
        if (quantity == 0) {
            throw new RuntimeException("Quantity has to be greater than zero");
        } else if (cartItem.getQuantity() == 0) {
            throw new RuntimeException("Quantity already set to low");
        } else if (cartItem.getQuantity() - quantity < 0) {
            throw new RuntimeException("Quantity must be less than or equal to " + cartItem.getQuantity());
        }
        cartItem.setQuantity(cartItem.getQuantity() - quantity);
        Cart cart = cartItem.getCart();
        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getMenuItem().getPrice() * quantity);
        cartItem.setCart(cart);
        return modelMapper.map(cartItemRepository.save(cartItem), CartItemDto.class);
    }

    @Override
    public void removeCartItemFromCart(CartItem cartItem) {
        cartItemRepository.deleteById(cartItem.getId());
    }

    @Override
    public Boolean isCartItemExist(MenuItem menuItem, Cart cart) {
        CartItem cartItem = cartItemRepository.findByMenuItemAndCartId(menuItem, cart.getId());
        if (!cartItem.equals(null)) {
            return true;
        }
        return false;
    }

    @Override
    public CartItem getCartItemByMenuItemAndCart(MenuItem menuItem, Cart cart) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCartItemByMenuItemAndCart'");
    }


}
