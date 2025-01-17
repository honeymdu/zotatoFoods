package com.food.zotatoFoods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmtpEmailDetailsDto {

    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;

}