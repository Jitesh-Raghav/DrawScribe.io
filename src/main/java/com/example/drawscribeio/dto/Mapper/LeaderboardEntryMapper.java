package com.example.drawscribeio.dto.Mapper;

import com.example.drawscribeio.dto.LeaderBoardEntryDto;
import com.example.drawscribeio.entity.LeaderBoard.LeaderBoardEntry;

public class LeaderboardEntryMapper {

    public static LeaderBoardEntryDto toLeaderBoardEntryDTO(LeaderBoardEntry entry) {
        if (entry == null) {
            return null;
        }

        LeaderBoardEntryDto dto = new LeaderBoardEntryDto();
        dto.setEntryId(entry.getEntryId());
        dto.setUserId(entry.getUser() != null ? entry.getUser().getUserId() : null);
        dto.setLeaderboardId(entry.getLeaderboard() != null ? entry.getLeaderboard().getLeaderboardId() : null);
        dto.setPoints(entry.getScore());

        return dto;
    }
}
