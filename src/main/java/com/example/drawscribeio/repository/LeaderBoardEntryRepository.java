package com.example.drawscribeio.repository;

import com.example.drawscribeio.entity.LeaderBoard.LeaderBoardEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderBoardEntryRepository extends JpaRepository<LeaderBoardEntry, Long> {
}
