package com.food.zotatoFoods.services;

import com.food.zotatoFoods.dto.MenuItemDto;
import com.food.zotatoFoods.entites.Menu;


public interface MenuItemService {

    public Menu addMenuItem(Long RestaurantId,MenuItemDto menuItem);

    public Menu getMenuByRestaurant(Long RestaurantId);

    public void setMenuItemStatus(Long RestaurantId, long menuItemId, Boolean isAvailable);

    public Menu removeMenuItem(Long RestaurantId, Long MenuItemId);
}
