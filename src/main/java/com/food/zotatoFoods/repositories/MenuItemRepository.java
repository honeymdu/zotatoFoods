package com.food.zotatoFoods.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.zotatoFoods.entites.MenuItem;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long>{

}
