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

    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<Role> role;
    private String contact;
    private String GstNumber;
    private List<AddressDto> addresses;

}
