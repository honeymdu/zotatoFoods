package com.food.zotatoFoods.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.OrderItem;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.entites.enums.OrderRequestStatus;
import com.food.zotatoFoods.entites.enums.OrderStatus;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.OrderRepository;
import com.food.zotatoFoods.services.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public Order updateOrderStatus(Long OrderId, OrderStatus orderStatus) {
        Order order = getOrderById(OrderId);
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long OrderId) {
        return orderRepository.findById(OrderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order Not Found with Order id =" + OrderId));
    }

    @Override
    public Order createOrder(OrderRequests orderRequests) {
        // check orderRequest status
        if (orderRequests.getOrderRequestStatus().equals(OrderRequestStatus.ACCEPTED)) {
            Order order = modelMapper.map(orderRequests, Order.class);
            order.setPickupLocation(orderRequests.getRestaurant().getRestaurantLocation());
            order.setDropoffLocation(orderRequests.getDropLocation());
            List<OrderItem> orderItems = orderRequests.getCart().getCartItems().stream()
                    .map(item -> {
                        OrderItem orderItem = modelMapper.map(item, OrderItem.class);
                        orderItem.setId(null);
                        orderItem.setOrder(order);
                        return orderItem;
                    }).collect(Collectors.toList());
            order.setOrderItems(orderItems);
            order.setOrderStatus(OrderStatus.ACCEPTED);
            return orderRepository.save(order);

        }
        throw new RuntimeException("Can not create order as OrderRequest status is not Accepted");
    }

    @Override
    public Order cancelOrder(Long OrderId) {
        Order order = getOrderById(OrderId);
        if (order.getOrderStatus().equals(OrderStatus.ACCEPTED)) {
            order.setOrderStatus(OrderStatus.CANCELLED);
            return orderRepository.save(order);

        }
        throw new RuntimeException("Can not cancel order as OrderRequest status is not Accepted earlier");
    }

}
