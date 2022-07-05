package com.academy.javabootcamp.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String image;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "car_id")
    private List<CarImage> images;

    @Column(nullable = false)
    private Integer year;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<CarTransfer> carTransfers;

    @ManyToOne
    @JoinColumn(name = "carCategory_id")
    private CarCategory carCategory;
}
