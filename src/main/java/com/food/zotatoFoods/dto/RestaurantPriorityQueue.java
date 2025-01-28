package com.food.zotatoFoods.dto;

import com.food.zotatoFoods.entites.Restaurant;

import lombok.Data;

@Data
public class RestaurantPriorityQueue implements Comparable<RestaurantPriorityQueue> {

    Restaurant restaurant;
    int priority;

    public RestaurantPriorityQueue(Restaurant restaurant, int priority) {
        this.restaurant = restaurant;
        this.priority = priority;
    }

    @Override
    public int compareTo(RestaurantPriorityQueue o) {
        return Integer.compare(o.priority, this.priority);
    }

    @Override
    public String toString() {
        return "RestaurantPriorityQueue{" +
                "restaurant=" + (restaurant != null ? restaurant.getName() : "null") +
                ", priority=" + priority +
                '}';
    }

}
