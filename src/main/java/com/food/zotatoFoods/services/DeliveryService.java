package com.food.zotatoFoods.services;

import org.locationtech.jts.geom.Point;

import com.food.zotatoFoods.entites.Order;

public interface DeliveryService {

    public void accecptDeliveryRequest(Order order);

    public void cancelDeliveryRequest(Order order);

    public void updateDeliveryStatus(Order order);

    public void viewOrderDetails(Order order);

    public Double CalculateDeliveryFees(Point PickupLocation, Point DropOffLocation);
    

}
