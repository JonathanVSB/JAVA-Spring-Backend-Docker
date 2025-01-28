package com.hackathon.inditex.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "centers", uniqueConstraints = @UniqueConstraint(columnNames = {"latitude", "longitude"}))
public class Center {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String capacity;

    private String status;

    private Integer currentLoad;

    private Integer maxCapacity;

    @Embedded
    private Coordinates coordinates;

    public Center() {}

    public Center(String name, String capacity, String status, Integer currentLoad, Integer maxCapacity, Coordinates coordinates) {
        setName(name);
        setCapacity(capacity);
        setStatus(status);
        setCurrentLoad(currentLoad);
        setMaxCapacity(maxCapacity);
        setCoordinates(coordinates);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        List<String> validCapacities = Arrays.asList("S", "M", "B", "SM", "SB", "MB", "SMB");
        if (!validCapacities.contains(capacity)) {
            throw new IllegalArgumentException("Invalid capacity. Allowed values: " + validCapacities);
        }
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        List<String> validStatuses = Arrays.asList("AVAILABLE", "OCCUPIED");
        if (!validStatuses.contains(status)) {
            throw new IllegalArgumentException("Invalid status. Allowed values: " + validStatuses);
        }
        this.status = status;
    }

    public Integer getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(Integer currentLoad) {
        this.currentLoad = currentLoad; // Validación externa en el servicio.
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        if (maxCapacity == null || maxCapacity <= 0) {
            throw new IllegalArgumentException("Max capacity must be greater than 0");
        }
        this.maxCapacity = maxCapacity;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null");
        }
        this.coordinates = coordinates;
    }
}
