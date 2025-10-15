package com.example.kalps.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kalps.entity.userentity;

public interface userRepository extends JpaRepository<userentity,Long>{

	
	Optional<userentity>findByUsername(String username);
}
