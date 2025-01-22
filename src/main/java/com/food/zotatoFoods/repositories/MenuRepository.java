package com.food.zotatoFoods.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.zotatoFoods.entites.Menu;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByRestaurantId(Long RestaurantId);

    Menu findByMenuName(String name);
}
