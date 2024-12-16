package com.example.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Min(value = 2, message = "street name must be at least 2 characters")
    private String street;

    @NotBlank
    @Min(value = 2, message = "landmark must be at least 2 characters")
    private String landmark;

    @NotBlank
    @Min(value = 2, message = "city name must be at least 2 characters")
    private String city;

    @NotBlank
    @Min(value = 2, message = "state name must be at least 2 characters")
    private String state;

    @NotBlank
    @Min(value = 2, message = "country name must be at least 2 characters")
    private String country;

    @NotBlank
    @Min(value = 6, message = "pincode must be at least 6 characters")
    private String pincode;

    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();
}
