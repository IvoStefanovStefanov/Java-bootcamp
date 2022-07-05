package com.academy.javabootcamp.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date created;

    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL)
    private List<CarTransfer> carTransfers;

    public String fullName(){
        return String.format("%s %s",name,surname);
    }
}
