package com.food.zotatoFoods.services.impl;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.services.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Override
    public void accecptDeliveryRequest(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accecptDeliveryRequest'");
    }

    @Override
    public void cancelDeliveryRequest(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelDeliveryRequest'");
    }

    @Override
    public void updateDeliveryStatus(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateDeliveryStatus'");
    }

    @Override
    public void viewOrderDetails(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewOrderDetails'");
    }

    @Override
    public Double CalculateDeliveryFees(Point PickupLocation, Point DropOffLocation) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'CalculateDeliveryFees'");
    }

}
