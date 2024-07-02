package com.uqblog.serverapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uqblog.serverapp.models.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUsername(String username);
    User findByEmail(String email);
}
