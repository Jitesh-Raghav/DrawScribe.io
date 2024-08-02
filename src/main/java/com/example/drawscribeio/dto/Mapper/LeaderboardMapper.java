package com.example.drawscribeio.dto.Mapper;

import com.example.drawscribeio.dto.LeaderBoardEntryDto;
import com.example.drawscribeio.dto.LeaderboardDto;
import com.example.drawscribeio.entity.LeaderBoard.Leaderboard;

import java.util.List;
import java.util.stream.Collectors;

public class LeaderboardMapper {

    public static LeaderboardDto toLeaderboardDTO(Leaderboard leaderboard) {
        if (leaderboard == null) {
            return null;
        }

        LeaderboardDto dto = new LeaderboardDto();
        dto.setLeaderboardId(leaderboard.getLeaderboardId());
        dto.setGameSessionId(leaderboard.getGameSession().getGame_sessionId());

        // Map entries

        List<LeaderBoardEntryDto
                        > entryDTOs = leaderboard.getEntries().stream()
                .map(LeaderboardEntryMapper::toLeaderBoardEntryDTO)
                .collect(Collectors.toList());
        dto.setEntries(entryDTOs);

        return dto;
    }

}
