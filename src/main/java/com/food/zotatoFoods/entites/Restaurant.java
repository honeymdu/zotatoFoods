package com.food.zotatoFoods.entites;

import java.util.List;

import lombok.*;
import org.locationtech.jts.geom.Point;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Restaurant", indexes = {
        @Index(name = "idx_restaurant_name", columnList = "name"),
        @Index(name = "idx_restaurant_isavailable", columnList = "isAvailable"),
})
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Point restaurantLocation;
    private Double rating = 0.0;
    private String GstNumber;
    private Boolean isAvailable = true;
    @OneToOne(mappedBy = "restaurant")
    private Menu menu;
    @ManyToOne
    private RestaurantPartner restaurantPartner;
    @OneToMany(mappedBy = "restaurant")
    private List<Order> Orders;
    @OneToMany(mappedBy = "restaurant")
    private List<OrderRequests> orderRequests;

}
