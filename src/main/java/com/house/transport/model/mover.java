package com.house.transport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class mover {
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
}
