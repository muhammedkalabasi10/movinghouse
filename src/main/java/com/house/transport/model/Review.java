package com.house.transport.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer_id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Mover mover_id;
    @Min(0)
    @Max(5)
    private int evaluation;
    private String comment;
}
