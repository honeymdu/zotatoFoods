package com.food.zotatoFoods.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.CreateMenu;
import com.food.zotatoFoods.dto.MenuItemDto;
import com.food.zotatoFoods.entites.Menu;
import com.food.zotatoFoods.entites.MenuItem;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.MenuRepository;
import com.food.zotatoFoods.services.MenuService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final ModelMapper modelMapper;

    private final MenuRepository menuRepository;

    @Override
    public Menu addMenuItem(Long RestaurantId, MenuItemDto menuItemDto) {
        MenuItem menuItem = modelMapper.map(menuItemDto, MenuItem.class);
        Menu menu = getMenuByRestaurant(RestaurantId);
        menuItem.setMenu(menu);
        menuItem.setRating(0.0);
        menu.getMenuItems().add(menuItem);
        return menuRepository.save(menu);
    }

    @Override
    public Boolean setMenuItemStatus(Long RestaurantId, long menuItemId, Boolean isAvailable) {
        Menu menu = getMenuByRestaurant(RestaurantId);
        List<MenuItem> menuItems = menu.getMenuItems();
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getId().equals(menuItemId)) {
                menuItem.setIsAvailable(isAvailable);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean removeMenuItem(Long RestaurantId, Long menuItemId) {
        Menu menu = getMenuByRestaurant(RestaurantId);
        List<MenuItem> menuItems = menu.getMenuItems();
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getId().equals(menuItemId)) {
                menuItems.remove(menuItem);
                return true;
            }
        }

        throw new ResourceNotFoundException("Menu Item Not Exist by Menu Item Id =" + menuItemId);
    }

    @Override
    public MenuItem getMenuItemById(Long RestaurantId, Long menuItemId) {
        Menu menu = getMenuByRestaurant(RestaurantId);
        List<MenuItem> menuItems = menu.getMenuItems();
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getId().equals(menuItemId)) {
                return menuItem;
            }
        }
        throw new ResourceNotFoundException("Menu Item not exist with menuItem Id =" + menuItemId);
    }

    @Override
    public Menu getMenuByRestaurant(Long RestaurantId) {
        return menuRepository.findByRestaurantId(RestaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not Found Restaurant"));
    }

    @Override
    public Menu CreateMenu(CreateMenu createMenu) {
        Menu menu = Menu.builder().menuItems(createMenu.getMenuItem()).menuName(createMenu.getName())
                .restaurant(createMenu.getRestaurant()).build();
        return menuRepository.save(menu);
    }

}
