package com.food.zotatoFoods.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.User;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer,Long>{

    Optional<Consumer> findByUser(User user);

}
