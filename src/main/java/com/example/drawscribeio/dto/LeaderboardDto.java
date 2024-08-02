package com.example.drawscribeio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter@Getter
@AllArgsConstructor@NoArgsConstructor
public class LeaderboardDto {
    private Long leaderboardId;
    private Long gameSessionId;
    private List<LeaderBoardEntryDto> entries;
}
