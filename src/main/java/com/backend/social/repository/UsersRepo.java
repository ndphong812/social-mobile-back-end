package com.backend.social.repository;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.social.entity.Users;


@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {

	List<Users> findAll();

	@Query(value = "SELECT * from user",nativeQuery = true)
	String getCurrentUser();

	Optional<Users> findById(Long id);

	Optional<Users> findByEmail(String email);

	Optional<Users> findByPhone(String phone);

	@Transactional
	@Modifying
	@Query("UPDATE Users a " +
			"SET a.enable = TRUE WHERE a.email = ?1")
	int enableUser(String email);
}
