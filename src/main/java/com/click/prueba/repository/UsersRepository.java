package com.click.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.click.prueba.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{
	Users findByUsername(String username);
}
