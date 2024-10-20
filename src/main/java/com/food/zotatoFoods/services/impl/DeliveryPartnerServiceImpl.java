package com.food.zotatoFoods.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.DeliveryPartnerDto;
import com.food.zotatoFoods.entites.DeliveryPartner;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.repositories.DeliveryPartnerRepository;
import com.food.zotatoFoods.services.DeliveryPartnerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final ModelMapper modelMapper;

    @Override
    public User rateDeliveryPartner(Long UserId, Integer rating) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rateDeliveryPartner'");
    }

    @Override
    public void acceptOrder(Long orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'acceptOrder'");
    }

    @Override
    public void cancelOrder(Long orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelOrder'");
    }

    @Override
    public void pickupOrderFromRestaurant(Long orderId, String restaurantOTP) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pickupOrderFromRestaurant'");
    }

    @Override
    public void startOrderRide(Long orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startOrderRide'");
    }

    @Override
    public void endOrderRide(Long orderId, String otp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endOrderRide'");
    }

    @Override
    public Page<DeliveryPartner> getAllDeliveryPartner(PageRequest pageRequest) {
        return deliveryPartnerRepository.findAll(pageRequest);
    }

    @Override
    public Boolean removeDeliveryPartner(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeDeliveryPartner'");
    }

    @Override
    public DeliveryPartnerDto AddNewDeliveryPartner(DeliveryPartner deliveryPartner) {
       return modelMapper.map(deliveryPartnerRepository.save(deliveryPartner),DeliveryPartnerDto.class);
    }

}
