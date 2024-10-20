package com.food.zotatoFoods.strategies;

public interface DeliveryFareCalculationStrategy {

    public static final double RIDE_FARE_MULTIPLYIER = 10;

    double calculateFare(Double totalPrice, Double distance);

}

