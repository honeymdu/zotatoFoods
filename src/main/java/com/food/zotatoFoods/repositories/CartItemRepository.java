package com.food.zotatoFoods.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.zotatoFoods.entites.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByMenuItemIdAndCartId(Long menuItemId, Long cartId);

    List<CartItem> findAllByCartId(Long cartId);

}
