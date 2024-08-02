package com.example.drawscribeio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaderBoardEntryDto{
    private Long entryId;
    private Long userId;
    private Long leaderboardId;
    private int points;
}
