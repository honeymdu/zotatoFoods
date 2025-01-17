package com.food.zotatoFoods.dto;


import com.food.zotatoFoods.entites.enums.PaymentMethod;

import lombok.Data;

@Data
public class OrderPriorityQueue implements Comparable<OrderPriorityQueue> {

    private Long id;
    private Long creationTime;
    private int restaurantPriority;
    private PaymentMethod paymentMethod;

    public OrderPriorityQueue(Long orderId, PaymentMethod paymentMethod, int restaurantPriority, Long creationTime) {
        this.id = orderId;
        this.creationTime = creationTime;
        this.restaurantPriority = restaurantPriority;
        this.paymentMethod = paymentMethod;
    }

    @Override
    public int compareTo(OrderPriorityQueue other) {

        // if ((this.creationTime - other.getCreationTime()) > TimeUnit.MINUTES.toMillis(5)) {
        //     return 5;
        // }

        if (this.restaurantPriority != other.restaurantPriority) {
            return Integer.compare(other.restaurantPriority, this.restaurantPriority);
        }
        // Define priority for payment types (UPI > Card > COD)
        int thisPaymentPriority = getOrderPriority(this.paymentMethod);
        int otherPaymentPriority = getOrderPriority(other.paymentMethod);
        return Integer.compare(otherPaymentPriority, thisPaymentPriority);
    }

    private int getOrderPriority(PaymentMethod paymentMethod) {
        if (paymentMethod.equals(PaymentMethod.UPI)) {
            return 3;
        } else if (paymentMethod.equals(PaymentMethod.WALLET)) {
            return 2;
        } else if (paymentMethod.equals(PaymentMethod.CASH)) {
            return 1;
        } else {
            return 0;
        }

    }
}
