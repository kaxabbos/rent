package com.rent.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cars {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private int price;
    private String[] photos;
    private boolean free;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CarsDescription description;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Statistics statistics;

    public Cars(String name, int price, boolean free, String[] photos) {
        this.name = name;
        this.price = price;
        this.free = free;
        this.photos = photos;
    }
}
