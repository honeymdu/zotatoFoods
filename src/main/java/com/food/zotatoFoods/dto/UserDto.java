package com.food.zotatoFoods.dto;

import java.util.*;
import com.food.zotatoFoods.entites.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;
    private String email;
    private Set<Role> role;
    private String contact;
    private List<AddressDto> addresses;

}
