package com.food.zotatoFoods.services;

import com.food.zotatoFoods.dto.MenuItemDto;
import com.food.zotatoFoods.entites.MenuItem;
import java.util.*;

public interface MenuItemService {

    public MenuItem addMenuItem(List<MenuItemDto> list);

    public List<MenuItem> getMenuByRestaurant(Long RestaurantId);

    public void setMenuItemStatus(Long RestaurantId, long menuItemId, Boolean isAvailable);

    public void removeMenuItem(Long RestaurantId, Long MenuItemId);
}
