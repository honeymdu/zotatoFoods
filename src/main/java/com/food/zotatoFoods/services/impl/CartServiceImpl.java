package com.food.zotatoFoods.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.CartDto;
import com.food.zotatoFoods.dto.CartItemDto;
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
    private final ModelMapper modelMapper;

    @Override
    public Cart createCart(Long restaurantId, Consumer consumer) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Cart cart = Cart.builder()
                .restaurant(restaurant)
                .consumer(consumer)
                .totalPrice(0.0)
                .validCart(true)
                .build();
        return cartRepository.save(cart);

    }

    @Override
    public CartDto addItemToCart(Long CartId, CartItem cartItem) {
        Cart cart = getCartById(CartId);
        isValidCart(cart);
        cartItem.setCart(cart);
        cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getMenuItem().getPrice() * cartItem.getQuantity()));
        Cart savedCart = cartRepository.save(cart);
        CartDto cartDto = modelMapper.map(savedCart, CartDto.class);
        List<CartItemDto> cartItemDtos = cartItemService.getAllCartItemsByCartId(cartDto.getId()).stream()
                .map(cartIt -> modelMapper.map(cartItem, CartItemDto.class)).collect(Collectors.toList());
        cartDto.setCartItems(cartItemDtos);
        return cartDto;
    }

    @Override
    public CartDto viewCart(Long CartId) {
        Cart cart = getCartById(CartId);
        CartDto cartDto = modelMapper.map(cart, CartDto.class);
        List<CartItemDto> cartItemDtos = cartItemService.getAllCartItemsByCartId(CartId).stream()
                .map(cartItem -> modelMapper.map(cartItem, CartItemDto.class)).collect(Collectors.toList());
        cartDto.setCartItems(cartItemDtos);
        return cartDto;
    }

    @Override
    public CartDto removeItemFromCart(Long CartId, CartItem cartItem) {
        Cart cart = getCartById(CartId);
        isValidCart(cart);
        Boolean isExist = cartItemService.isCartItemExist(cartItem, cart);
        if (!isExist) {
            throw new RuntimeException("can not remove cartItem as this is not exist in Cart with cart id = " + CartId);
        }
        List<CartItem> cartItems = cartItemService.getAllCartItemsByCartId(CartId).stream()
                .map(CartItemsList -> modelMapper.map(cartItem, CartItem.class)).collect(Collectors.toList());
        for (CartItem cartItem2 : cartItems) {
            if (cartItem2.equals(cartItem)) {
                if (cartItem2.getQuantity() > 1) {
                    cartItemService.decrementCartItemQuantity(1, cartItem);
                } else {
                    cartItemService.removeCartItemFromCart(cartItem2);
                }
            }
        }
        Cart cart2 = getCartById(CartId);
        CartDto cartDto = modelMapper.map(cart2, CartDto.class);
        List<CartItemDto> cartItemDtos = cartItemService.getAllCartItemsByCartId(cartDto.getId()).stream()
                .map(cartIt -> modelMapper.map(cartItem, CartItemDto.class)).collect(Collectors.toList());
        cartDto.setCartItems(cartItemDtos);
        return cartDto;
    }

    @Override
    public Cart clearCartItemFromCart(Long CartId) {
        Cart cart = getCartById(CartId);
        isValidCart(cart);
        List<CartItem> cartItems = cartItemService.getAllCartItemsByCartId(CartId);
        for (CartItem cartItem2 : cartItems) {
            cartItemService.removeCartItemFromCart(cartItem2);
        }
        Cart Updatedcart = getCartById(CartId);
        Updatedcart.setTotalPrice(0.0);
        cartRepository.save(Updatedcart);
        return getCartById(CartId);
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
    public Boolean isValidCartExist(Consumer consumer, Long restaurantId) {
        Cart cart = cartRepository.findByConsumerIdAndRestaurantIdAndValidCart(consumer.getId(), restaurantId, true)
                .orElse(null);
        if (cart == null) {
            return false;
        }
        return true;
    }

    @Override
    public Cart getCartByConsumerIdAndRestaurantId(Long id, Long restaurantId) {
        return cartRepository.findByConsumerIdAndRestaurantIdAndValidCart(id, restaurantId, true)
                .orElseThrow(() -> new RuntimeException(
                        "cart not found with the consume id =" + id + " And Restaurant Id = " + restaurantId));
    }

    @Override
    public void deleteAllCartItemByCartId(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not Found with cart id =" + cartId));

        clearCartItemFromCart(cartId);

        inValidCart(cart);
    }

    @Override
    public CartDto prepareCart(Consumer consumer, Long RestaurantId, Long MenuItemId) {
        // check cart already exist with current Restaurent Id
        Boolean isValidCartExist = isValidCartExist(consumer, RestaurantId);

        if (!isValidCartExist) {
            Cart cart = createCart(RestaurantId, consumer);
            MenuItem menuItem = menuService.getMenuItemById(RestaurantId, MenuItemId);
            CartItem cartItem = cartItemService.createNewCartItem(menuItem, cart);
            return addItemToCart(cart.getId(), cartItem);
        }

        Cart cart = getCartByConsumerIdAndRestaurantId(consumer.getId(), RestaurantId);
        MenuItem menuItem = menuService.getMenuItemById(RestaurantId, MenuItemId);
        if (cartItemService.isMenuItemExistInCart(menuItem, cart)) {
            CartItem cartItem = cartItemService.getCartItemByMenuItemAndCart(menuItem, cart);
            cartItemService.incrementCartItemQuantity(1, cartItem);
            CartDto cartDto = modelMapper.map(
                    getCartByConsumerIdAndRestaurantId(consumer.getId(), RestaurantId),
                    CartDto.class);
            List<CartItemDto> cartItemDtos = cartItemService.getAllCartItemsByCartId(cartDto.getId()).stream()
                    .map(cartItems -> modelMapper.map(cartItem, CartItemDto.class)).collect(Collectors.toList());

            cartDto.setCartItems(cartItemDtos);
            return cartDto;
        }

        CartItem cartItem = cartItemService.createNewCartItem(menuItem, cart);
        return addItemToCart(cart.getId(), cartItem);
    }

}
