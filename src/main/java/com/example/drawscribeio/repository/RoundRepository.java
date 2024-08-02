package com.example.drawscribeio.repository;

import com.example.drawscribeio.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoundRepository extends JpaRepository<Round, Long> {
    @Query("SELECT r FROM Round r WHERE r.gameSessions.game_sessionId = :gameSessionId AND r.isCurrentRound = true")
    Optional<Round> findByGameSessionAndCurrentRound(@Param("gameSessionId") Long gameSessionId);
}
