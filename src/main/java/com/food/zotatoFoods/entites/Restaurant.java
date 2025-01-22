package com.food.zotatoFoods.entites;

import java.util.List;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point restaurantLocation;
    private String gstNumber;
    private Double rating;
    private Boolean isAvailable;
    private Boolean isVarified;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_partner_id", nullable = false)
    private RestaurantPartner restaurantPartner;
    @OneToMany(mappedBy = "restaurant")
    private List<Order> Orders;
    @OneToMany(mappedBy = "restaurant")
    private List<OrderRequests> orderRequests;

}
