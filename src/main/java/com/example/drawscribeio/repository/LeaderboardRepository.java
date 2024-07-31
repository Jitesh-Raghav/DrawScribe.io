package com.example.drawscribeio.repository;

import com.example.drawscribeio.entity.LeaderBoard.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {
}
