package com.food.zotatoFoods.entites;

import java.util.List;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Boolean available;
    private String vehicleId;
    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point currentLocation;
    @OneToMany(mappedBy = "deliveryPartner")
    private List<DeliveryRequest> deliveryRequest;

}
