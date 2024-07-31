package com.example.drawscribeio.repository;

import com.example.drawscribeio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
