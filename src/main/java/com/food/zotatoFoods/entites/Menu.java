package com.food.zotatoFoods.entites;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Menu", indexes = {
        @Index(name = "idx_menu_restaurant_id", columnList = "restaurant_id"),
        @Index(name = "idx_menu_isactive", columnList = "isActive")
})
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String menuName;
    @OneToMany(mappedBy = "menu")
    private List<MenuItem> menuItems;
    private Boolean isActive;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
