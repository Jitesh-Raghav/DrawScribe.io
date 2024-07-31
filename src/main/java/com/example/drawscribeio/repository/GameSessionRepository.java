package com.example.drawscribeio.repository;

import com.example.drawscribeio.entity.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameSessionRepository extends JpaRepository<GameSession,Long> {
}
