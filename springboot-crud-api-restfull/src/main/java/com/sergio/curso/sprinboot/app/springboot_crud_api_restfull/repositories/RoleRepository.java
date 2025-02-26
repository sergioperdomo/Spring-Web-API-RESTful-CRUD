package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.repositories;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Método para buscar un role por su nombre en neustra base de datos
    Optional<Role> findByName(String name);

}
