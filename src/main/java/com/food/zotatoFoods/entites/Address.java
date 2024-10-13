package com.food.zotatoFoods.entites;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "Address", indexes = {
        @Index(name = "idx_address_user_id", columnList = "user_id")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private Point UserLocation;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
