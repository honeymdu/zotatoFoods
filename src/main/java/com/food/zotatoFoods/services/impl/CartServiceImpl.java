package com.food.zotatoFoods.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;
import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.MenuItem;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.exceptions.InvalidCartException;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.CartRepository;
import com.food.zotatoFoods.services.CartItemService;
import com.food.zotatoFoods.services.CartService;
import com.food.zotatoFoods.services.MenuService;
import com.food.zotatoFoods.services.RestaurantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final CartItemService cartItemService;

    @Override
    public Cart createCart(Long restaurantId, Consumer consumer) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Cart cart = Cart.builder()
                .restaurant(restaurant)
                .consumer(consumer)
                .totalPrice(0.0)
                .ValidCart(true)
                .cartItems(new ArrayList<>())
                .build();
        return cartRepository.save(cart);

    }

    @Override
    public Cart addItemToCart(Long CartId, CartItem cartItem) {
        Cart cart = getCartById(CartId);
        isValidCart(cart);
        cart.getCartItems().add(cartItem);
        cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getMenuItem().getPrice() * cartItem.getQuantity()));
        return cartRepository.save(cart);
    }

    @Override
    public Cart viewCart(Long CartId) {
        Cart cart = getCartById(CartId);
        return cart;
    }

    @Override
    public Cart removeItemFromCart(Long CartId, CartItem cartItem) {
        Cart cart = getCartById(CartId);
        isValidCart(cart);
        cart.getCartItems().remove(cartItem);
        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getMenuItem().getPrice() * cartItem.getQuantity()));
        return cartRepository.save(cart);
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

    @Override
    public void isValidCart(Cart cart) {
        if (!cart.getValidCart()) {
            throw new InvalidCartException("Cart is not valid with cartId " + cart.getId());
        }
        // check is the restaurent is active or not if not invalid the cart
        if (!cart.getRestaurant().getIsAvailable()) {
            inValidCart(cart);
            throw new InvalidCartException("Cart is not valid with cartId " + cart.getId());
        }
    }

    @Override
    public void inValidCart(Cart cart) {
        cart.setValidCart(false);
        saveCart(cart);
    }

    @Override
    public Boolean isValidCartExist(Consumer consumer, Long RestaurantId) {
        Cart cart = getCartByConsumerIdAndRestaurantId(consumer.getId(), RestaurantId);
        if (!cart.getValidCart()) {
            return false;
        }
        return true;
    }

    @Override
    public Cart getCartByConsumerIdAndRestaurantId(Long id, Long restaurantId) {
        return cartRepository.findByConsumerIdAndRestaurantId(id, restaurantId)
                .orElseThrow(() -> new RuntimeException(
                        "cart not found with the consume id =" + id + " And Restaurant Id = " + restaurantId));
    }

    @Override
    public void deleteAllCartItemByCartId(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not Found with cart id =" + cartId));

        List<CartItem> cartItems = cart.getCartItems();

        for (CartItem cartItem : cartItems) {
            removeItemFromCart(cartId, cartItem);
        }

        inValidCart(cart);
    }

    @Override
    public Cart prepareCart(Consumer consumer, Long RestaurantId, Long MenuItemId) {
        // check cart already exist with current Restaurent Id
        Boolean isValidCartExist = isValidCartExist(consumer, RestaurantId);

        if (!isValidCartExist) {
            Cart cart = createCart(RestaurantId, consumer);
            MenuItem menuItem = menuService.getMenuItemById(RestaurantId, MenuItemId);
            CartItem cartItem = cartItemService.createNewCartItem(menuItem, cart);
            return addItemToCart(cart.getId(), cartItem);
        }

        Cart cart = getCartByConsumerIdAndRestaurantId(RestaurantId, consumer.getId());
        MenuItem menuItem = menuService.getMenuItemById(RestaurantId, MenuItemId);
        if (cartItemService.isCartItemExist(menuItem, cart)) {
            CartItem cartItem = cartItemService.getCartItemByMenuItemAndCart(menuItem, cart);
            cartItemService.incrementCartItemQuantity(1, cartItem);
        }

        return cart;
    }

}
