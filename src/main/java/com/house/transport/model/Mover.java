package com.house.transport.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mover {
    @Id
    @GeneratedValue
    private int id;
    private String email;
    private String phone;
    private String password;
    private String company_name;
    private String logo;
    private String about;
    private String licences_information;
    @Lob
    private byte[] k1_certificate;
    @Lob
    private byte[] k3_certificate;
    private long vkn;
    private int max_floor;
    @OneToMany(mappedBy = "mover_id")
    @JsonIgnore
    private List<Review> reviewList;
}
