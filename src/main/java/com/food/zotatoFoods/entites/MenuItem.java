package com.food.zotatoFoods.entites;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.zotatoFoods.entites.enums.FoodCategory;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "MenuItem", indexes = {
        @Index(name = "idx_menuitem_name", columnList = "name"),
        @Index(name = "idx_menuitem_foodcategory", columnList = "foodCategory")
})
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private String name;
    private String dishDescription;
    private Double price;
    @Enumerated(EnumType.STRING)
    private FoodCategory foodCategory;
    private List<String> ingredients;
    private Double rating;
    private Boolean isAvailable;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @JsonIgnore
    private Menu menu;

}
