package com.academy.javabootcamp.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    @Column
    private Long days;

    @Column(nullable = false)
    private Integer adults;

    @Column
    private Integer kids;

    @Column
    private String typeBed;

    @Column
    private String typeView;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column
    @Temporal(TemporalType.DATE)
    private Date created;

    @Column(nullable = false)
    private String status;
}
