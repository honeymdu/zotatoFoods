package com.food.zotatoFoods.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.entites.MenuItem;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.RestaurantPartner;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.RestaurantRepository;
import com.food.zotatoFoods.services.RestaurantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    @Override
    public Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new ResourceNotFoundException("Restaurant Not Found with Restaurant ID =" + restaurantId));
    }

    @Override
    public Restaurant viewProfile(Long restaurantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProfile'");
    }

    @Override
    public Page<Restaurant> findAllRestaurant(Pageable pageRequest) {
        return restaurantRepository.findAll(pageRequest);
    }

    @Override
    public List<Restaurant> getRestaurantByRestaurantPartner(RestaurantPartner restaurantPartner) {
        return restaurantRepository.findByRestaurantPartner(restaurantPartner);
    }

    @Override
    public Boolean removeRestaurant(Long RestaurantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeRestaurant'");
    }

    @Override
    public Restaurant AddNewRestaurant(RestaurantPartner restaurantPartner, RestaurantDto restaurantDto) {
        Restaurant restaurant = modelMapper.map(restaurantDto, Restaurant.class);
        List<OrderRequests> orderRequest = new ArrayList<>();
        restaurant.setIsAvailable(true);
        restaurant.setIsVarified(false);
        restaurant.setOrderRequests(orderRequest);
        restaurant.setRating(0.0);
        restaurant.setRestaurantPartner(restaurantPartner);
        Restaurant savedRestaurant = save(restaurant);
        return savedRestaurant;

    }

    @Override
    public Boolean IsRestaurentAlreadyExist(Restaurant newRestaurant) {
        Optional<Restaurant> restaurant = restaurantRepository.findByNameAndRestaurantPartner(newRestaurant.getName(),
                newRestaurant.getRestaurantPartner());
        return restaurant.isPresent();
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);

    }

    @Override
    public Page<Restaurant> getAllVarifiedRestaurant(PageRequest pageRequest) {
        return restaurantRepository.findByIsAvailableAndIsVarified(pageRequest, true, true);
    }

    @Override
    public Page<MenuItem> viewMenu(Long restaurantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMenuFromRestaurant'");
    }

}
