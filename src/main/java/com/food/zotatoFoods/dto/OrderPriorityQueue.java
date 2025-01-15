package com.food.zotatoFoods.dto;

import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.enums.PaymentMethod;

import lombok.Data;

@Data
public class OrderPriorityQueue implements Comparable<OrderPriorityQueue> {

    Order order;
    PaymentMethod priority;

    public OrderPriorityQueue(Order order, PaymentMethod priority) {
        this.order = order;
        this.priority = priority;
    }

    @Override
    public int compareTo(OrderPriorityQueue o) {
        if (o.priority.equals(PaymentMethod.UPI)) {
            return 1;
        } else if (o.priority.equals(PaymentMethod.WALLET)) {
            return 0;
        } else {
            return -1;
        }
    }

}
