package com.example.drawscribeio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
public class GameSessionDto {
    private Long gameSessionId;
    private String sessionCode;
    private List<UserDto> users;
    private List<ScoreDto> scores;
    private List<RoundDto> rounds;
    private LeaderboardDto leaderboard;
}
