package com.food.zotatoFoods.services;

import java.util.List;

import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.User;

public interface AdminService {

    public void onBoardNewRestaurant(Restaurant restaurant);

    public User onBoardDeliveryPartner(User user, String VehicleId);

    List<Restaurant> getAllRestaurant();

    List<User> getAllDeliveryPartner();

    public void removeDeliveryPartner(User user, String VehicleId);

    public void removeRestaurant(Restaurant restaurant);

}
