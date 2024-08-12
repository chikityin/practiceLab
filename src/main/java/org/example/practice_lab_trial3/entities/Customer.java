package org.example.practice_lab_trial3.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import jakarta.persistence.*;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerNumber;

    private String customerName;
    private double customerDeposit;
    private int numberOfYears;
    private String savingsType;
}
