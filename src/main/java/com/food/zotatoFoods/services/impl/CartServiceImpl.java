package com.food.zotatoFoods.services.impl;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.CartDto;
import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;
import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.exceptions.InvalidCartException;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.CartRepository;
import com.food.zotatoFoods.services.CartService;
import com.food.zotatoFoods.services.ConsumerService;
import com.food.zotatoFoods.services.RestaurantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private RestaurantService restaurantService;
    private ConsumerService consumerService;
    private ModelMapper modelMapper;

    @Override
    public CartDto createCart(Long ConsumerId, Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Consumer consumer = consumerService.getConsumerById(ConsumerId);
        Cart cart = Cart.builder()
                .foodAmount(0.0)
                .restaurant(restaurant)
                .user(consumer)
                .deliveryFee(0.0)
                .totalPrice(0.0)
                .ValidCart(true)
                .cartItems(new ArrayList<>())
                .build();
        return modelMapper.map(cartRepository.save(cart), CartDto.class);

    }

    @Override
    public CartDto addItemToCart(Long CartId, CartItem cartItem) {
        IsvalidCart(CartId);
        Cart cart = getCartById(CartId);
        cart.getCartItems().add(cartItem);
        cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getMenuItem().getPrice() * cartItem.getQuantity()));
        return modelMapper.map(cartRepository.save(cart), CartDto.class);
    }

    private void IsvalidCart(Long cartId) {
        Cart cart = getCartById(cartId);
        if (!cart.getValidCart()) {
            throw new InvalidCartException("Cart is not valid with cartId " + cartId);
        }
    }

    @Override
    public CartDto viewCart(Long CartId) {
        Cart cart = getCartById(CartId);
        return modelMapper.map(cart, CartDto.class);
    }

    @Override
    public CartDto removeItemFromCart(Long CartId, CartItem cartItem) {
        IsvalidCart(CartId);
        Cart cart = getCartById(CartId);
        cart.getCartItems().remove(cartItem);
        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getMenuItem().getPrice() * cartItem.getQuantity()));
        return modelMapper.map(cartRepository.save(cart), CartDto.class);
    }

    @Override
    public void isValidCart(Long CartId) {
        Cart cart = cartRepository.findById(CartId)
                .orElseThrow(() -> new ResourceNotFoundException("cart is not exist with CartId " + CartId));
        if (!cart.getValidCart()) {
            throw new InvalidCartException("Cart is Not Valid with CartId " + CartId);
        }
    }

    @Override
    public Cart getCartById(Long CartId) {
        return cartRepository.findById(CartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not Exist with CartId " + CartId));
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

}
