package com.example.drawscribeio.repository;

import com.example.drawscribeio.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
}
