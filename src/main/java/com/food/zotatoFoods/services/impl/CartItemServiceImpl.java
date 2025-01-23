package com.food.zotatoFoods.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.CartItemDto;
import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;
import com.food.zotatoFoods.entites.MenuItem;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.CartItemRepository;
import com.food.zotatoFoods.repositories.CartRepository;
import com.food.zotatoFoods.services.CartItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;

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
        cartItem.setTotalPrice(cartItem.getMenuItem().getPrice() * cartItem.getQuantity());
        cartItem.setCart(cart);
        return modelMapper.map(cartItemRepository.save(cartItem), CartItemDto.class);
    }

    @Override
    public Cart decrementCartItemQuantity(Integer quantity, CartItem cartItem) {
        if (quantity == 0) {
            throw new RuntimeException("Quantity has to be greater than zero");
        } else if (cartItem.getQuantity() == 0) {
            throw new RuntimeException("Quantity already set to low");
        } else if (cartItem.getQuantity() - quantity < 0) {
            throw new RuntimeException("Quantity must be less than or equal to " + cartItem.getQuantity());
        }
        cartItem.setQuantity(cartItem.getQuantity() - quantity);
        Cart cart = cartItem.getCart();
        cartItem.setTotalPrice(cartItem.getMenuItem().getPrice() * cartItem.getQuantity());
        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getMenuItem().getPrice() * quantity);
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);
        return cartRepository.save(cartItem.getCart());
    }

    @Override
    public void removeCartItemFromCart(CartItem cartItem) {
        Cart cart = cartItem.getCart();
        cartItemRepository.deleteById(cartItem.getId());
        cartRepository.save(cart);

    }

    @Override
    public Boolean isCartItemExist(CartItem cartItem, Cart cart) {
        List<CartItem> cartItems = getAllCartItemsByCartId(cart.getId());
        for (CartItem cartItem2 : cartItems) {
            if (cartItem2 != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Boolean isMenuItemExistInCart(MenuItem MenuItem, Cart cart) {
        CartItem cartItem = cartItemRepository.findByMenuItemIdAndCartId(MenuItem.getId(), cart.getId());
        if (cartItem != null) {
            return true;
        }
        return false;
    }

    @Override
    public CartItem getCartItemByMenuItemAndCart(MenuItem menuItem, Cart cart) {
        if (!isMenuItemExistInCart(menuItem, cart)) {
            throw new ResourceNotFoundException("MenuItem not Exist with cart Id =" + menuItem.getId());
        }
        return cartItemRepository.findByMenuItemIdAndCartId(menuItem.getId(), cart.getId());

    }

    @Override
    public List<CartItem> getAllCartItemsByCartId(Long cartId) {
        return cartItemRepository.findAllByCartId(cartId);

    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

}
