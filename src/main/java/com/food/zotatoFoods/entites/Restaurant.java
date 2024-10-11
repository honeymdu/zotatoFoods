package com.food.zotatoFoods.entites;

import java.util.List;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Restaurant", indexes = {
        @Index(name = "idx_restaurant_name", columnList = "name")
})
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Point restaurantLocation;
    private Double rating;
    private Boolean isAvailable;

    @OneToMany(mappedBy = "restaurant")
    private List<MenuItem> menuItems;
    @ManyToOne
    private User restaurantOwner;

}
