package com.backend.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.social.entity.Users;


@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
	List<Users> findAll();
}
