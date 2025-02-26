package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)// Elimina registros en la tabla intermedia si el usuario es eliminado
    private List<Role> roles;

    @Transient
    private boolean admin; // No es un campo que esta mapeado en la tabla.

}
