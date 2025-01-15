package com.food.zotatoFoods.repositories;

import java.util.List;
import java.util.Optional;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.food.zotatoFoods.entites.DeliveryPartner;
import com.food.zotatoFoods.entites.User;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, Long> {

        @Query(value = "SELECT d.* "
                        + "FROM DeliveryPartner d " +
                        "WHERE d.available = true AND ST_DWithin(d.current_location , :pickupLocation, 15000) "
                        + "ORDER BY  d.rating DESC "
                        + "LIMIT 10", nativeQuery = true)
        List<DeliveryPartner> findTenNearByTopRatedDeliveryPartner(Point pickupLocation);

        @Query(value = "SELECT d.* "
                        + "FROM DeliveryPartner d " +
                        "WHERE d.available = true AND ST_DWithin(d.current_location , :pickupLocation, 15000) "
                        + "LIMIT 10", nativeQuery = true)
        List<DeliveryPartner> findTenNearByDeliveryPartner(Point pickupLocation);

        DeliveryPartner findByUserId(Long userId);

        Optional<DeliveryPartner> findByUser(User user);

}
