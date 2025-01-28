package com.hackathon.inditex.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private String size;

    private String status;

    private String assignedCenter;

    @Embedded
    private Coordinates coordinates;

    // Constructor vacío necesario para JPA
    public Order() {
    }

    // Constructor básico
    public Order(Long customerId, String size, String status, String assignedCenter, Coordinates coordinates) {
        this.customerId = customerId;
        this.size = size;
        this.status = status;
        this.assignedCenter = assignedCenter;
        this.coordinates = coordinates;
    }
}

