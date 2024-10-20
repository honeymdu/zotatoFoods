package com.food.zotatoFoods.services.impl;

import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElse(null);
    }

    public User getUserFromId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with Id =" + userId));
    }

    public User getUserByEmail(String Email) {
        return userRepository.findByEmail(Email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with email =" + Email));
    }

    public User findUserByEmail(String Email) {
        return userRepository.findByEmail(Email)
                .orElse(null);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
