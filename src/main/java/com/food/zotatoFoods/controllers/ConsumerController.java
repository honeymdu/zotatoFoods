package com.food.zotatoFoods.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.zotatoFoods.dto.AddressDto;
import com.food.zotatoFoods.dto.CartDto;
import com.food.zotatoFoods.dto.ConsumerOTP;
import com.food.zotatoFoods.dto.CreateOrderRequest;
import com.food.zotatoFoods.dto.CreateOrderRequestDto;
import com.food.zotatoFoods.dto.MenuDto;
import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.dto.PreOrderRequestDto;
import com.food.zotatoFoods.entites.Address;
import com.food.zotatoFoods.entites.Menu;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.repositories.AddressRepository;
import com.food.zotatoFoods.services.ConsumerService;
import com.food.zotatoFoods.services.impl.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/consumer")
@RequiredArgsConstructor
@Secured("ROLE_CONSUMER")
public class ConsumerController {

        private final ModelMapper modelMapper;
        private final ConsumerService consumerService;
        private final UserService userService;
        private final AddressRepository addressRepository;

        @PostMapping("/prepareCart/{RestaurantId}/{MenuItemId}")
        public ResponseEntity<CartDto> prepareMyCart(@PathVariable Long RestaurantId, @PathVariable Long MenuItemId) {
                CartDto cart = consumerService.PrepareCart(RestaurantId, MenuItemId);
                return ResponseEntity.ok(cart);

        }

        @PostMapping("/update-address/{UserId}")
        public ResponseEntity<?> addNewAddress(@PathVariable Long UserId,
                        @RequestBody AddressDto addressDto) {
                // check user exist
                User user = userService.findUserById(UserId);
                Address address = modelMapper.map(addressDto, Address.class);
                address.setUser(user);
                return new ResponseEntity<>(addressRepository.save(address), HttpStatus.CREATED);

        }

        @PostMapping("/remove-cartItem/{cartId}/{cartItemId}")
        public ResponseEntity<CartDto> removeCartItem(@PathVariable Long cartId, @PathVariable Long cartItemId) {
                CartDto cart = consumerService.removeCartItem(cartId, cartItemId);
                return ResponseEntity.ok(cart);
        }

        @PostMapping("/clear-cartItems/{RestaurantId}")
        public ResponseEntity<?> clearCartItem(@PathVariable Long RestaurantId) {
                consumerService.clearCart(RestaurantId);
                return ResponseEntity.notFound().build();
        }

        @PostMapping("/view-pre-order-request/{RestaurantId}")
        public ResponseEntity<PreOrderRequestDto> viewPreOrderRequest(@PathVariable Long RestaurantId,
                        @RequestBody CreateOrderRequestDto createOrderRequestDto) {
                CreateOrderRequest createOrderRequest = modelMapper.map(createOrderRequestDto,
                                CreateOrderRequest.class);
                return ResponseEntity.ok(consumerService.viewPreOrderRequest(RestaurantId,
                                createOrderRequest.getUserLocation()));
        }

        @PostMapping("/create-order-request/{RestaurantId}")
        public ResponseEntity<OrderRequestsDto> createOrderRequest(@PathVariable Long RestaurantId,
                        @RequestBody CreateOrderRequestDto createOrderRequestDto) {
                CreateOrderRequest createOrderRequest = modelMapper.map(createOrderRequestDto,
                                CreateOrderRequest.class);
                OrderRequestsDto orderRequestsDto = consumerService.createOrderRequest(RestaurantId,
                                createOrderRequest);
                return ResponseEntity.ok(orderRequestsDto);
        }

        @GetMapping("/list/get-available-restaurant")
        public ResponseEntity<Page<Restaurant>> viewAvailableRestaurant(
                        @RequestParam(defaultValue = "0") Integer PageOffset,
                        @RequestParam(defaultValue = "10", required = false) Integer PageSize) {
                PageRequest pageRequest = PageRequest.of(PageOffset, PageSize,
                                Sort.by(Sort.Direction.DESC, "id"));
                Page<Restaurant> restaurants = consumerService.getAllRestaurant(pageRequest);
                return ResponseEntity.ok(restaurants);
        }

        @GetMapping("/view-menu/{RestaurantId}")
        public ResponseEntity<MenuDto> viewMenu(@PathVariable Long RestaurantId) {
                Menu menu = consumerService.viewMenuByRestaurantId(RestaurantId);
                return ResponseEntity.ok(modelMapper.map(menu, MenuDto.class));
        }

        @GetMapping("/view-cart/{RestaurantId}")
        public ResponseEntity<CartDto> viewCart(@PathVariable Long RestaurantId) {
                return ResponseEntity.ok(consumerService.viewCart(RestaurantId));
        }

        @GetMapping("/get-consumer-otp/{OrderId}")
        public ResponseEntity<ConsumerOTP> getConsumerOtp(@PathVariable Long OrderId) {
                return ResponseEntity.ok(consumerService.getOtpByOrderId(OrderId));
        }

}
