package com.project.habitat.backend.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "app_users")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer uid;

    @Column(name = "firstname")
    String firstName;

    @Column(name = "lastname")
    String lastName;

    @Column(name = "username")
    String username;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;
}
