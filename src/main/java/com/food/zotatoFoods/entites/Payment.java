package com.food.zotatoFoods.entites;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

import com.food.zotatoFoods.entites.enums.PaymentMethod;
import com.food.zotatoFoods.entites.enums.PaymentStatus;

@Entity
@Table(name = "Payment", indexes = {
        @Index(name = "idx_payment_paymentstatus", columnList = "paymentStatus")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "payment")
    private Order order;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @CreationTimestamp
    private LocalDateTime paymentTime;

}
