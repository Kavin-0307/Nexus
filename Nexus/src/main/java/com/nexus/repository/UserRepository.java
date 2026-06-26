package com.nexus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexus.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	Optional<User> findByUserEmail(String userEmail);
	Optional<User> findByUserName(String userName);
}
