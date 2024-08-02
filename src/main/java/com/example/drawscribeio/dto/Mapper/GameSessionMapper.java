package com.example.drawscribeio.dto.Mapper;

import com.example.drawscribeio.dto.GameSessionDto;
import com.example.drawscribeio.dto.RoundDto;
import com.example.drawscribeio.dto.ScoreDto;
import com.example.drawscribeio.dto.UserDto;
import com.example.drawscribeio.entity.GameSession;
import com.example.drawscribeio.entity.LeaderBoard.Leaderboard;

import java.util.List;
import java.util.stream.Collectors;

public class GameSessionMapper {
    public static GameSessionDto toGameSessionDTO(GameSession gameSession) {
        if (gameSession == null) {
            return null;
        }

        GameSessionDto dto = new GameSessionDto();
        dto.setGameSessionId(gameSession.getGame_sessionId());
        dto.setSessionCode(gameSession.getSession_code());

        // Map users
        List<UserDto> userDTOs = gameSession.getUsers().stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
        dto.setUsers(userDTOs);

        // Map scores
        List<ScoreDto> scoreDTOs = gameSession.getScores().stream()
                .map(ScoreMapper::toScoreDTO)
                .collect(Collectors.toList());
        dto.setScores(scoreDTOs);

        // Map rounds
        List<RoundDto> roundDTOs = gameSession.getRounds().stream()
                .map(RoundMapper::toRoundDTO)
                .collect(Collectors.toList());
        dto.setRounds(roundDTOs);

        // Map leaderboard
        Leaderboard leaderboard = gameSession.getLeaderboards();
        dto.setLeaderboard(LeaderboardMapper.toLeaderboardDTO(leaderboard));

        return dto;
    }

//    public static GameSession toGameSessionEntity(GameSessionDTO dto) {
//        if (dto == null) {
//            return null;
//        }
//
//        GameSession gameSession = new GameSession();
//        gameSession.setGame_sessionId(dto.getGameSessionId());
//        gameSession.setSession_code(dto.getSessionCode());
//
//        // Map users
//        List<User> users = dto.getUsers().stream()
//                .map(UserMapper::toUserEntity)
//                .collect(Collectors.toList());
//        gameSession.setUsers(users);
//
//        // Map scores
//        List<Score> scores = dto.getScores().stream()
//                .map(ScoreMapper::toScoreEntity)
//                .collect(Collectors.toList());
//        gameSession.setScores(scores);
//
//        // Map rounds
//        List<Round> rounds = dto.getRounds().stream()
//                .map(RoundMapper::toRoundEntity)
//                .collect(Collectors.toList());
//        gameSession.setRounds(rounds);
//
//        // Map leaderboard
//        Leaderboard leaderboard = LeaderboardMapper.toLeaderboardEntity(dto.getLeaderboard());
//        gameSession.setLeaderboards(leaderboard);
//
//        return gameSession;
//    }
}
