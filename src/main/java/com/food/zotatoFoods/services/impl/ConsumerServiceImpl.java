package com.food.zotatoFoods.services.impl;

import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;
import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.MenuItem;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.enums.OrderRequestStatus;
import com.food.zotatoFoods.entites.enums.PaymentMethod;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.ConsumerRepository;
import com.food.zotatoFoods.services.CartItemService;
import com.food.zotatoFoods.services.CartService;
import com.food.zotatoFoods.services.ConsumerService;
import com.food.zotatoFoods.services.DeliveryService;
import com.food.zotatoFoods.services.OrderRequestService;
import com.food.zotatoFoods.services.RestaurantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;
    private final OrderRequestService orderRequestService;
    private final RestaurantService restaurantService;
    private final ModelMapper modelMapper;
    private final CartService cartService;
    private final DeliveryService deliveryService;
    private final CartItemService cartItemService;
    Double PLATFORM_COMMISSION = 10.5;

    @Override
    public Boolean rateRestaurant(Long RestaurantId, Double rating) {
        Restaurant restaurant = restaurantService.getRestaurantById(RestaurantId);
        restaurant.setRating(rating);
        restaurantService.save(restaurant);
        return true;
    }

    @Override
    public Consumer createNewConsumer(User user) {
        Consumer consumer = Consumer.builder().user(user).rating(0.0).build();
        return consumerRepository.save(consumer);
    }

    @Override
    public Consumer getConsumerById(Long consumerId) {
        return consumerRepository.findById(consumerId)
                .orElseThrow(() -> new ResourceNotFoundException("Consumer not found with consumer id =" + consumerId));
    }

    @Override
    public OrderRequestsDto createOrderRequest(Cart cart, PaymentMethod paymentMethod, Point UserLocation) {

        cartService.isValidCart(cart);
        Double delivery_price = deliveryService.CalculateDeliveryFees(cart.getRestaurant().getRestaurantLocation(),
                UserLocation);
        OrderRequests orderRequests = OrderRequests.builder()
                .cart(cart)
                .consumer(cart.getConsumer())
                .deliveryFee(delivery_price)
                .platformFee(PLATFORM_COMMISSION)
                .foodAmount(cart.getTotalPrice())
                .orderRequestStatus(OrderRequestStatus.PENDING)
                .restaurant(cart.getRestaurant())
                .paymentMethod(paymentMethod)
                .totalPrice(cart.getTotalPrice() + delivery_price + PLATFORM_COMMISSION).build();

        // Send Notification to Corresponding restaurant

        OrderRequests savedOrderRequests = orderRequestService.save(orderRequests);
        cartService.inValidCart(cart);
        OrderRequestsDto orderRequestsDto = modelMapper.map(savedOrderRequests, OrderRequestsDto.class);
        return orderRequestsDto;
    }

    @Override
    public Boolean cancelOrderRequest(Long OrderRequestId) {
        OrderRequests orderRequests = orderRequestService.getOrderRequestById(OrderRequestId);

        if (orderRequests.getConsumer().equals(getCurrentConsumer())) {
            throw new RuntimeException(
                    "Consumer does not own this Order Request with id: " + OrderRequestId);
        }

        if (orderRequests.getOrderRequestStatus().equals(OrderRequestStatus.ACCEPTED)) {
            throw new RuntimeException(
                    "Order Request can not be canceled with Order Request status = " + OrderRequestStatus.ACCEPTED);
        }

        orderRequests.setOrderRequestStatus(OrderRequestStatus.CANCELLED);
        orderRequestService.save(orderRequests);
        return true;
    }

    @Override
    public Consumer getCurrentConsumer() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return consumerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Rider Not Found assosicated with user with id =" + user.getId()));
    }

    @Override
    public Cart PrepareCart(Long RestaurantId, MenuItem menuItem) {
        Consumer consumer = getCurrentConsumer();
        // check cart already exist with current Restaurent Id
        Boolean isValidCartExist = cartService.isValidCartExist(consumer.getId(), RestaurantId);

        if (!isValidCartExist) {
            Cart cart = cartService.createCart(consumer.getId(), RestaurantId);
            CartItem cartItem = cartItemService.createNewCartItem(menuItem, cart);
            return cartService.addItemToCart(cart.getId(), cartItem);
        }

        Cart cart = cartService.getCartByConsumerIdAndRestaurantId(consumer.getId(), RestaurantId);

        if (cartItemService.isCartItemExist(menuItem, cart)) {
            CartItem cartItem = cartItemService.getCartItemByMenuItemAndCart(menuItem, cart);
            cartItemService.incrementCartItemQuantity(1, cartItem);
        }

        return cart;
    }

    @Override
    public Cart removeCartItemFromCart(Long CartId, CartItem cartItem) {
        Consumer consumer = getCurrentConsumer();
        // check cart already exist with current Restaurent Id
        Cart cart = cartService.getCartById(CartId);
        Boolean isValidCartExist = cartService.isValidCartExist(consumer.getId(), cart.getRestaurant().getId());

        if (!isValidCartExist) {
            throw new RuntimeException("Can not Remove Item as cart not exists");
        }

        return cartService.removeItemFromCart(CartId, cartItem);
    }

    @Override
    public void clearCart(Long CartId) {
        cartService.deleteAllCartItemByCartId(CartId);
    }

}
