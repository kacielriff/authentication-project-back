package com.kacielriff.authentication_project.repository;

import com.kacielriff.authentication_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("SELECT u.active FROM User u WHERE u.email = :email")
    Boolean findActiveByEmail(@Param("email") String email);
}