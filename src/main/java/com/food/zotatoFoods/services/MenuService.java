package com.food.zotatoFoods.services;

import com.food.zotatoFoods.dto.CreateMenu;
import com.food.zotatoFoods.dto.MenuItemDto;
import com.food.zotatoFoods.entites.Menu;
import com.food.zotatoFoods.entites.MenuItem;

public interface MenuService {

    Menu addMenuItem(Long RestaurantId, MenuItemDto menuItem);

    Boolean setMenuItemStatus(Long RestaurantId, long menuItemId, Boolean isAvailable);

    Boolean removeMenuItem(Long RestaurantId, Long MenuItemId);

    MenuItem getMenuItemById(Long RestaurantId, Long MenuItemId);

    Menu getMenuByRestaurant(Long RestaurantId);

    Menu CreateMenu(CreateMenu createMenu);

    Menu getMenuById(Long MenuItemId);

    Boolean checkMenuItemExistByName(Long RestaurantId, MenuItem MenuItem);

}
