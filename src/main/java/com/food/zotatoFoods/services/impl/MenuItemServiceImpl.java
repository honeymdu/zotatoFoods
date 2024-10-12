package com.food.zotatoFoods.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.MenuItemDto;
import com.food.zotatoFoods.entites.MenuItem;
import com.food.zotatoFoods.services.MenuItemService;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Override
    public MenuItem addMenuItem(List<MenuItemDto> list) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addMenuItem'");
    }

    @Override
    public List<MenuItem> getMenuByRestaurant(Long RestaurantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMenuByRestaurant'");
    }

    @Override
    public void setMenuItemStatus(Long RestaurantId, long menuItemId, Boolean isAvailable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setMenuItemStatus'");
    }

    @Override
    public void removeMenuItem(Long RestaurantId, Long MenuItemId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeMenuItem'");
    }

}
