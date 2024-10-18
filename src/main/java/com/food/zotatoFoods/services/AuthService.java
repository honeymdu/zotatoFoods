package com.food.zotatoFoods.services;

import com.food.zotatoFoods.dto.ConsumerDto;
import com.food.zotatoFoods.dto.RestaurantPartnerDto;
import com.food.zotatoFoods.dto.SignUpDto;
import com.food.zotatoFoods.dto.UserDto;

public interface AuthService {

    String[] login(String email, String password);

    UserDto consumerSignUp(SignUpDto signupDto);

    UserDto RestaurantOwnerSignUp(RestaurantPartnerDto restaurantOwnerSignUpDto);

    String refreshToken(String refreshToken);

}
