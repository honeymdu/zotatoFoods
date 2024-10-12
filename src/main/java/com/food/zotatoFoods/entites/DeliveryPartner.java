package com.food.zotatoFoods.entites;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "DeliveryPartner", indexes = {
        @Index(name = "idx_deliverypartner_available", columnList = "available")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rating;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Boolean available=true;
    private String vehicleId;
    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point currentLocation;
    @OneToMany(mappedBy = "deliveryPartner")
    private List<DeliveryItem> deliveryItems = new ArrayList<>();

}
