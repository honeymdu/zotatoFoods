package com.food.zotatoFoods.entites;

import java.util.List;

import lombok.*;
import org.locationtech.jts.geom.Point;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
        @Index(name = "idx_restaurant", columnList = "restaurantOwner_id")
})
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Point restaurantLocation;
    private Double rating=0.0;
    private Boolean isAvailable=true;
    @OneToMany(mappedBy = "restaurant")
    private List<MenuItem> menuItems;
    @ManyToOne
    private User restaurantOwner;
    @OneToMany(mappedBy = "restaurant")
    private List<Order> Orders;

}
