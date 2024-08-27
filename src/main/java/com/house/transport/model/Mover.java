package com.house.transport.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @Pattern(message = "Please enter a valid phone number", regexp = "^(\\+0?1\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$\n")
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
    @Size(min = 10, max = 10)
    private String vkn;
    @Min(0)
    private int max_floor;
    @OneToMany(mappedBy = "mover_id")
    @JsonIgnore
    private List<Review> reviewList;
}
