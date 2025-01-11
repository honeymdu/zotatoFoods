package com.food.zotatoFoods.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.zotatoFoods.dto.CreateOrderRequest;
import com.food.zotatoFoods.dto.MenuDto;
import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.Menu;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.services.ConsumerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/consumer")
@RequiredArgsConstructor
@Secured("ROLE_CONSUMER")
public class ConsumerController {

        private final ModelMapper modelMapper;
        private final ConsumerService consumerService;

        @GetMapping("/list/get-available-restaurant")
        public ResponseEntity<Page<Restaurant>> viewAvailableRestaurant(
                        @RequestParam(defaultValue = "0") Integer PageOffset,
                        @RequestParam(defaultValue = "10", required = false) Integer PageSize) {
                PageRequest pageRequest = PageRequest.of(PageOffset, PageSize,
                                Sort.by(Sort.Direction.DESC, "id"));
                Page<Restaurant> restaurants = consumerService.getAllRestaurant(pageRequest);
                return ResponseEntity.ok(restaurants);
        }

        @PostMapping("/prepareCart/{RestaurantId}/{MenuItemId}")
        public ResponseEntity<Cart> prepareMyCart(@PathVariable Long RestaurantId, @PathVariable Long MenuItemId) {
                Cart cart = consumerService.PrepareCart(RestaurantId, MenuItemId);
                return ResponseEntity.ok(cart);

        }

        @PostMapping("/prepareCart/{RestaurantId}")
        public ResponseEntity<MenuDto> viewMenu(@PathVariable Long RestaurantId) {
                Menu menu = consumerService.viewMenuByRestaurantId(RestaurantId);
                return ResponseEntity.ok(modelMapper.map(menu, MenuDto.class));
        }

        @PostMapping("/create-order-request/{cartId}")
        public ResponseEntity<OrderRequestsDto> createOrderRequest(@PathVariable Long cartId,
                        @RequestBody CreateOrderRequest createOrderRequest) {
                OrderRequestsDto orderRequestsDto = consumerService.createOrderRequest(cartId, createOrderRequest);
                return ResponseEntity.ok(orderRequestsDto);
        }

}
