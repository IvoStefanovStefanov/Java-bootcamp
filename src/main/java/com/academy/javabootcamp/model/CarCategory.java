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
@Table(name = "carCategory")
public class CarCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "carCategory")
    private List<Car> car;

    @Column
    private String name;

    @Column
    private Integer seats;

    @Column
    private Double price;

    @Column
    private Integer carNumber;
}
