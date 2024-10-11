package com.food.zotatoFoods.services;

import java.util.List;

import org.locationtech.jts.geom.Point;

import com.food.zotatoFoods.entites.Restaurant;

public interface DistanceService {

    double CalculateDistance(Point src, Point dest);

    List<Restaurant> getNearestRestaurant(Point UserSrc);

}
