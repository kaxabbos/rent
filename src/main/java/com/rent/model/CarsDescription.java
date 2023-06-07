package com.rent.model;

import com.rent.model.enums.BodyType;
import com.rent.model.enums.Fuel;
import com.rent.model.enums.Transmission;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CarsDescription {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    private BodyType bodyType;
    @Enumerated(EnumType.STRING)
    private Fuel fuel;
    @Enumerated(EnumType.STRING)
    private Transmission transmission;
    private int average;
    private String description;
    private int capacity;

    public CarsDescription(BodyType bodyType, Fuel fuel, Transmission transmission, int average, String description, int capacity) {
        this.bodyType = bodyType;
        this.fuel = fuel;
        this.transmission = transmission;
        this.average = average;
        this.description = description;
        this.capacity = capacity;
    }
}
