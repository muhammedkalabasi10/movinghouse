package com.house.transport.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class review {
    @Id
    @GeneratedValue
    private int id;

    private int customer_id;
    private int mover_id;
    private int evaluation;
    private String comment;
}
