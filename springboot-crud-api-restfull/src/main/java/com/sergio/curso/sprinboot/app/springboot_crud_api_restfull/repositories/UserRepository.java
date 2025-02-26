package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.repositories;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //    Método para poder buscar un usuario mediante su nombre
    Optional<User> findByUsername(String username);

    //    Método para poder verificar si un usuario existe en nuestra base de datos.
    boolean existsByUsername(String username);

}
