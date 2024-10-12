package com.food.zotatoFoods.dto;

import com.food.zotatoFoods.entites.enums.Role;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantOwnerSignUpDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<Role> role;
    private String contact;
    @NonNull
    private String GSTNumber;
    @NonNull
    private String FSSAIlicense;
    private List<AddressDto> addresses;

}
