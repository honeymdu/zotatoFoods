package com.food.zotatoFoods.dto;

import lombok.Data;

@Data
public class SignUpDto {

    private String name;
    private String email;
    private String password;
    private String contact;
    private AddressDto addresses;

}
