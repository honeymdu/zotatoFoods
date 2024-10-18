package com.food.zotatoFoods.services.impl;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.RestaurantPartnerDto;
import com.food.zotatoFoods.dto.SignUpDto;
import com.food.zotatoFoods.dto.UserDto;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.enums.Role;
import com.food.zotatoFoods.exceptions.RuntimeConfilictException;
import com.food.zotatoFoods.security.JWTService;
import com.food.zotatoFoods.services.AuthService;
import com.food.zotatoFoods.services.ConsumerService;
import com.food.zotatoFoods.services.WalletService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ConsumerService consumerService;
    private final WalletService walletService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;

    @Override
    public String[] login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        User user = (User) authentication.getPrincipal();
        String AccessToken = jwtService.GenerateAccessToken(user);
        String RefreshToken = jwtService.GenerateRefreshToken(user);
        return new String[] { AccessToken, RefreshToken };
    }

    @Override
    @Transactional
    public UserDto consumerSignUp(SignUpDto signupDto) {
        User user = userService.findUserByEmail(signupDto.getEmail());
        if (user != null)
            throw new RuntimeConfilictException(
                    "Cannot signup, User already exists with email " + signupDto.getEmail());

        User mappedUser = modelMapper.map(signupDto, User.class);
        mappedUser.setRole(Set.of(Role.CONSUMER));
        mappedUser.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        User savedUser = userService.save(mappedUser);
        consumerService.createNewConsumer(savedUser);
        walletService.createNewWallet(savedUser);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public String refreshToken(String refreshToken) {

        Long UserId = jwtService.getUserIdFromToken(refreshToken);
        User user = userService.getUserFromId(UserId);
        return jwtService.GenerateAccessToken(user);
    }

    @Override
    public UserDto RestaurantOwnerSignUp(RestaurantPartnerDto restaurantOwnerSignUpDto) {
       return null;
    }

}
