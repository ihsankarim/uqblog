package com.uqblog.serverapp.models.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 25, unique = true)
    private String username;

    @Column(length = 25)
    private String password;

    @Column(length = 255)
    private String email;

    private boolean isEnabled = true;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @Enumerated(EnumType.STRING)
    private Role roles;
}
