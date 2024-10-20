package com.food.zotatoFoods.controllers;

import com.food.zotatoFoods.dto.LoginRequestDto;
import com.food.zotatoFoods.dto.LoginResponceDto;
import com.food.zotatoFoods.dto.RestaurantPartnerDto;
import com.food.zotatoFoods.dto.SignUpDto;
import com.food.zotatoFoods.dto.UserDto;
import com.food.zotatoFoods.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/SignUp")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signupDto) {
        return new ResponseEntity<>(authService.consumerSignUp(signupDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponceDto> login(@RequestBody LoginRequestDto LoginRequestDto,
            HttpServletResponse response) {
        String token[] = authService.login(LoginRequestDto.getEmail(), LoginRequestDto.getPassword());

        Cookie cookie = new Cookie("refreshToken", token[1]);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(new LoginResponceDto(token[0]));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the cookies"));
        String AccessToken = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(new LoginResponceDto(AccessToken));
    }

}
