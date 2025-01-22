package com.food.zotatoFoods.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Builder
public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
    private Double rating;
}
