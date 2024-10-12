package com.food.zotatoFoods.entites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Consumer", indexes = {
        @Index(name = "idx_consumer_user_id", columnList = "user_id")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    private Double rating;

}
