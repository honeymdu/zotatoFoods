package com.food.zotatoFoods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointDto {

    private double[] coordianates;

    private String type = "Point";

    public PointDto(double[] coordianates) {
        this.coordianates = coordianates;
    }

}