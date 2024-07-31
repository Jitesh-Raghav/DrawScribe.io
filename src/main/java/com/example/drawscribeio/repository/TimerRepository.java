package com.example.drawscribeio.repository;

import com.example.drawscribeio.entity.Timer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerRepository extends JpaRepository<Timer, Long> {
}
