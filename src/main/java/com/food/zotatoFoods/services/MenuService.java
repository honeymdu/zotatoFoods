package com.food.zotatoFoods.services;

import com.food.zotatoFoods.dto.CreateMenu;
import com.food.zotatoFoods.dto.MenuItemDto;
import com.food.zotatoFoods.entites.Menu;
import com.food.zotatoFoods.entites.MenuItem;

public interface MenuService {

    public Menu addMenuItem(Long RestaurantId, MenuItemDto menuItem);

    public Boolean setMenuItemStatus(Long RestaurantId, long menuItemId, Boolean isAvailable);

    public Boolean removeMenuItem(Long RestaurantId, Long MenuItemId);

    public MenuItem getMenuItemById(Long RestaurantId, Long MenuItemId);

    public Menu getMenuByRestaurant(Long RestaurantId);

    public Menu CreateMenu(CreateMenu createMenu);

    public Menu getMenuById(Long MenuItemId);

    public Boolean checkMenuItemExistByName(Long RestaurantId, MenuItem MenuItem);

}
