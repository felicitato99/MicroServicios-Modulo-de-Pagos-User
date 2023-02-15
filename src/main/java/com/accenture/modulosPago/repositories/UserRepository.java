package com.accenture.modulosPago.repositories;

import com.accenture.modulosPago.entities.User;
import com.accenture.modulosPago.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    User findByDni(String dni);
}
