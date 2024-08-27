package com.house.transport.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer_id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Mover mover_id;
    @Size(min = 0, max = 5)
    private int evaluation;
    private String comment;
}
