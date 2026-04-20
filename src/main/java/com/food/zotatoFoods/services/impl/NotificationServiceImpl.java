package com.food.zotatoFoods.services.impl;

import com.food.zotatoFoods.dto.SmtpEmailDetailsDto;
import com.food.zotatoFoods.entites.DeliveryPartner;
import com.food.zotatoFoods.entites.DeliveryRequest;
import com.food.zotatoFoods.services.EmailSenderService;
import com.food.zotatoFoods.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final EmailSenderService emailSenderService;

    @Override
    public void notifyDeliveryPartners(List<DeliveryPartner> partners, DeliveryRequest deliveryRequest) {
        Long orderId = deliveryRequest.getOrder().getId();
        partners.forEach(partner -> {
            String email = partner.getUser().getEmail();
            String body = String.format(
                    "Hello %s,\n\nA new delivery request is available.\nOrder ID: %d\nPickup: %s\nDrop: %s\n\nOpen the app to accept.",
                    partner.getUser().getName(), orderId,
                    deliveryRequest.getPickupLocation(), deliveryRequest.getDropLocation());
            emailSenderService.sendSimpleMail(
                    new SmtpEmailDetailsDto(email, body, "ZotatoFoods - New Delivery Request #" + orderId, null));
            log.info("Notified delivery partner {} for order {}", email, orderId);
        });
    }

    @Override
    public void notifyRestaurantOtp(DeliveryRequest deliveryRequest) {
        String email = deliveryRequest.getOrder().getRestaurant()
                .getRestaurantPartner().getUser().getEmail();
        Long orderId = deliveryRequest.getOrder().getId();
        String body = String.format(
                "Hello,\n\nYour pickup OTP for Order #%d is: %s\n\nShare this only with the delivery partner.",
                orderId, deliveryRequest.getRestaurantOtp());
        emailSenderService.sendSimpleMail(
                new SmtpEmailDetailsDto(email, body, "ZotatoFoods - Pickup OTP for Order #" + orderId, null));
        log.info("Sent restaurant OTP to {} for order {}", email, orderId);
    }

    @Override
    public void notifyConsumerOtp(DeliveryRequest deliveryRequest) {
        String email = deliveryRequest.getOrder().getConsumer().getUser().getEmail();
        Long orderId = deliveryRequest.getOrder().getId();
        String body = String.format(
                "Hello,\n\nYour delivery OTP for Order #%d is: %s\n\nShare this only with the delivery partner upon arrival.",
                orderId, deliveryRequest.getConsumerOtp());
        emailSenderService.sendSimpleMail(
                new SmtpEmailDetailsDto(email, body, "ZotatoFoods - Delivery OTP for Order #" + orderId, null));
        log.info("Sent consumer OTP to {} for order {}", email, orderId);
    }
}
