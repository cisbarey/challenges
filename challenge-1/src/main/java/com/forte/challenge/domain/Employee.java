package com.forte.challenge.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String position;
    private String department;
    private LocalDate dateOfJoining;
    private BigDecimal salary;

    @Column(unique = true)
    private String email;

}
