package com.food.zotatoFoods.services;

import com.food.zotatoFoods.dto.SignUpDto;
import com.food.zotatoFoods.dto.UserDto;

public interface AuthService {

    String[] login(String email, String password);

    UserDto SignUp(SignUpDto signupDto);

    String refreshToken(String refreshToken);

}
