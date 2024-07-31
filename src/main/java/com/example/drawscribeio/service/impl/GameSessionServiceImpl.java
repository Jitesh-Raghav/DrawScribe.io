package com.example.drawscribeio.service.impl;

import com.example.drawscribeio.entity.GameSession;
import com.example.drawscribeio.entity.LeaderBoard.LeaderBoardEntry;
import com.example.drawscribeio.entity.LeaderBoard.Leaderboard;
import com.example.drawscribeio.entity.Round;
import com.example.drawscribeio.entity.Score;
import com.example.drawscribeio.entity.User;
import com.example.drawscribeio.repository.GameSessionRepository;
import com.example.drawscribeio.service.GameSessionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class GameSessionServiceImpl implements GameSessionService {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    public GameSessionServiceImpl(GameSessionRepository gameSessionRepository) {
        this.gameSessionRepository=gameSessionRepository;
    }

    @Override
    public List<GameSession> getAllGameSessions() {
        return gameSessionRepository.findAll();
    }

    @Override
    public GameSession getGameSessionById(Long gameSessionId) {
        GameSession gameSession = gameSessionRepository.findById(gameSessionId).orElseThrow(() -> new RuntimeException("GameSession with given Id not found"));
        return gameSession;
    }


    @Override
    public GameSession createGameSession(GameSession gameSession) {
//        // Initialize related entities
//        Set<Score> scores = new HashSet<>();
//        Set<Round> rounds = new HashSet<>();
//        Set<User> users = new HashSet<>();
//        Leaderboard leaderboard = new Leaderboard();
//
//        // Initialize related entities as needed
//        // e.g., scores.add(new Score(...));
//
//        // Set relationships
//        gameSessionDetails.setScores(scores);
//        gameSessionDetails.setRounds(rounds);
//        gameSessionDetails.setUsers(users);
//        gameSessionDetails.setLeaderboards(leaderboard);
//        GameSession savedGameSession= gameSessionRepository.save(gameSessionDetails);
//        return savedGameSession;

        // Generate a unique game session code
        String gameSessionCode = UUID.randomUUID().toString();
        gameSession.setSession_code(gameSessionCode);


        // Ensure the leader board has the reference to the game session
        Leaderboard leaderBoard = gameSession.getLeaderboards();
        if (leaderBoard != null) {
            leaderBoard.setGameSession(gameSession);
            // Set the leader board in all entries
            for (LeaderBoardEntry entry : leaderBoard.getEntries()) {
                entry.setLeaderboard(leaderBoard);
            }
        }
        // Persist the game session and its leaderboards
        GameSession savedgameSession = gameSessionRepository.save(gameSession);

        return savedgameSession;
    }

    @Override
    public void deleteGameSession(Long gameSessionId) {
        gameSessionRepository.deleteById(gameSessionId);
    }
}
