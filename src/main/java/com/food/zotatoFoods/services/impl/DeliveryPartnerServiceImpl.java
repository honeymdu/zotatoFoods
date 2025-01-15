package com.food.zotatoFoods.services.impl;

import java.time.LocalDateTime;

import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.DeliveryPartnerDto;
import com.food.zotatoFoods.entites.DeliveryPartner;
import com.food.zotatoFoods.entites.DeliveryRequest;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.enums.DeliveryRequestStatus;
import com.food.zotatoFoods.entites.enums.OrderStatus;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.DeliveryPartnerRepository;
import com.food.zotatoFoods.repositories.DeliveryRequestRepository;
import com.food.zotatoFoods.services.DeliveryPartnerService;
import com.food.zotatoFoods.services.DistanceService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final ModelMapper modelMapper;
    private final DeliveryRequestRepository deliveryRequestRepository;
    private final DistanceService distanceService;

    @Override
    public DeliveryPartnerDto save(DeliveryPartner deliveryPartner) {
        return modelMapper.map(deliveryPartnerRepository.save(deliveryPartner), DeliveryPartnerDto.class);
    }

    @Override
    public void rateDeliveryPartner(Long UserId, Double rating) {
        DeliveryPartner deliveryPartner = deliveryPartnerRepository.findByUserId(UserId);
        deliveryPartner.setRating(deliveryPartner.getRating() + rating);
        save(deliveryPartner);
    }

    @Override
    public void completeOrderDelivery(Long deliveryRequestId, String consumerOtp) {
        DeliveryRequest deliveryRequest = deliveryRequestRepository.findById(deliveryRequestId)
                .orElseThrow(() -> new RuntimeException("DeliveryRequest Not Found =" + deliveryRequestId));
        if (deliveryRequest.getDeliveryRequestStatus().equals(DeliveryRequestStatus.ACCEPTED)
                && deliveryRequest.getDeliveryPartner().equals(getCurrentDeliveryPartner())) {
            throw new RuntimeException(
                    "Can not Complete the order as this Delivery Request is assosiated with current partner, DeliverRequestStatus = "
                            + deliveryRequest.getDeliveryRequestStatus());
        }

        if (deliveryRequest.getConsumerOtp().equals(consumerOtp)) {
            throw new RuntimeException("OTP NOT ACCEPTED");
        }
        deliveryRequest.setDeliveryRequestStatus(DeliveryRequestStatus.COMPLETED);
        deliveryRequest.getOrder().setOrderStatus(OrderStatus.DELIVERED);
        deliveryRequestRepository.save(deliveryRequest);
    }

    @Override
    public Page<DeliveryPartner> getAllDeliveryPartner(PageRequest pageRequest) {
        return deliveryPartnerRepository.findAll(pageRequest);
    }

    @Override
    public void pickupOrderFromRestaurant(Long deliveryRequestId, String restaurantOTP) {
        DeliveryRequest deliveryRequest = deliveryRequestRepository.findById(deliveryRequestId)
                .orElseThrow(() -> new RuntimeException("DeliveryRequest Not Found =" + deliveryRequestId));
        if (deliveryRequest.getDeliveryRequestStatus().equals(DeliveryRequestStatus.ACCEPTED)
                && deliveryRequest.getDeliveryPartner().equals(getCurrentDeliveryPartner())) {
            throw new RuntimeException(
                    "Can not Picked the order as this Delivery Request is assosiated with current partner, DeliverRequestStatus = "
                            + deliveryRequest.getDeliveryRequestStatus());
        }

        if (deliveryRequest.getRestaurantOtp().equals(restaurantOTP)) {
            throw new RuntimeException("OTP NOT ACCEPTED");
        }
        deliveryRequest.getOrder().setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
        Long distance = (long) distanceService.CalculateDistance(deliveryRequest.getPickupLocation(),
                deliveryRequest.getDropLocation());
        deliveryRequest.setDeliveryTime(LocalDateTime.now().plusMinutes(2 * distance));
        deliveryRequestRepository.save(deliveryRequest);
    }

    @Override
    @Transactional
    public void acceptDeliveryRequest(Long deliveryRequestId) {
        DeliveryRequest deliveryRequest = deliveryRequestRepository.findById(deliveryRequestId)
                .orElseThrow(() -> new RuntimeException("DeliveryRequest Not Found =" + deliveryRequestId));

        if (deliveryRequest.getDeliveryRequestStatus().equals(DeliveryRequestStatus.ACCEPTED)) {
            throw new RuntimeException("Can not accept delivery Request as Delivery Request Status ="
                    + deliveryRequest.getDeliveryRequestStatus());
        }

        deliveryRequest.setDeliveryRequestStatus(DeliveryRequestStatus.ACCEPTED);
        getCurrentDeliveryPartner().setAvailable(false);
        deliveryRequest.getOrder().setOrderStatus(OrderStatus.DELIVERY_PARTNER_ASSIGNED);
        deliveryRequest.setDeliveryPartner(getCurrentDeliveryPartner());
        deliveryRequestRepository.save(deliveryRequest);
    }

    @Override
    public void cancelDeliveryRequest(Long deliveryRequestId) {
        DeliveryRequest deliveryRequest = deliveryRequestRepository.findById(deliveryRequestId)
                .orElseThrow(() -> new RuntimeException("DeliveryRequest Not Found =" + deliveryRequestId));
        if (deliveryRequest.getDeliveryRequestStatus().equals(DeliveryRequestStatus.ACCEPTED)) {
            throw new RuntimeException("Can not cancel delivery Request as Delivery Request Status ="
                    + deliveryRequest.getDeliveryRequestStatus());
        }
        deliveryRequest.setDeliveryRequestStatus(DeliveryRequestStatus.PENDING);
        deliveryRequest.getOrder().setOrderStatus(OrderStatus.READY_FOR_PICKUP);
        getCurrentDeliveryPartner().setAvailable(true);
        deliveryRequestRepository.save(deliveryRequest);

    }

    @Override
    public DeliveryPartner getCurrentDeliveryPartner() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return deliveryPartnerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Delivery Partner Not Found assosicated with user with id =" + user.getId()));
    }

    @Override
    public Point getCurrentLocation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentLocation'");
    }

}
