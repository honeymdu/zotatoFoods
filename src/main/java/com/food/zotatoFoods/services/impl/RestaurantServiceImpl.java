package com.food.zotatoFoods.services.impl;

import java.util.List;
import java.util.Optional;

import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.entites.Menu;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.RestaurantPartner;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.RestaurantRepository;
import com.food.zotatoFoods.services.MenuService;
import com.food.zotatoFoods.services.RestaurantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;
    private final MenuService menuService;

    @Override
    public Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new ResourceNotFoundException("Restaurant Not Found with Restaurant ID =" + restaurantId));
    }

    @Override
    public Restaurant viewProfile(Long restaurantId) {
        return getRestaurantById(restaurantId);
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
    public Restaurant AddNewRestaurant(RestaurantPartner restaurantPartner, RestaurantDto restaurantDto) {
        Restaurant restaurant = modelMapper.map(restaurantDto, Restaurant.class);
        restaurant.setIsAvailable(true);
        restaurant.setIsVarified(false);
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
    public Menu viewMenu(Long restaurantId) {
        return menuService.getMenuByRestaurant(restaurantId);
    }

    @Override
    public List<Restaurant> getAllVarifiedAndActiveRestaurant() {
        return restaurantRepository.findByIsAvailableAndIsVarified(true, true);
    }

    @Override
    public List<Restaurant> getTopTenNearestRestaurants(Point UserSrc) {
        return restaurantRepository.findTopTenNearestRestaurant(UserSrc);
    }

    @Override
    public void IsRestaurentActiveOrVarified(Long restaurantId) {

        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Restaurant Not Exist with Id =" + restaurantId);
        }

        if (!restaurantRepository.existsByIdAndIsAvailableTrueAndIsVarifiedTrue(restaurantId)) {
            throw new RuntimeException("Restaurant is not available for orders at the moment with Id =" + restaurantId);
        }
    }

}
