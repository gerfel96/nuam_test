package com.nuam.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "The name cannot be empty")
    @Size(min = 3, message = "The name must have at least 3 characters")
    private String name;

    private String description;

    @Min(value = 0, message = "The price must be greater than zero")
    private BigDecimal price;

    private LocalDateTime createdAt = LocalDateTime.now();


}
