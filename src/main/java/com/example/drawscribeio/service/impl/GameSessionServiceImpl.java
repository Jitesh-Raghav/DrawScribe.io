package com.example.drawscribeio.service.impl;

import com.example.drawscribeio.entity.GameSession;
import com.example.drawscribeio.entity.LeaderBoard.LeaderBoardEntry;
import com.example.drawscribeio.entity.LeaderBoard.Leaderboard;
import com.example.drawscribeio.entity.Round;
import com.example.drawscribeio.entity.Score;
import com.example.drawscribeio.entity.User;
import com.example.drawscribeio.repository.*;
import com.example.drawscribeio.service.GameSessionService;
import jakarta.persistence.EntityNotFoundException;
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
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @Autowired
    private LeaderBoardEntryRepository leaderBoardEntryRepository;


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
    public GameSession createGameSession(GameSession gameSession, Long userId) {
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

        User user = userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found with given id " + userId));
        gameSession.getUsers().add(user);

        // Ensure the leaderboard is initialized
        Leaderboard leaderBoard = gameSession.getLeaderboards();
        if (leaderBoard == null) {
            leaderBoard = new Leaderboard();
            leaderBoard.setGameSession(gameSession);
            gameSession.setLeaderboards(leaderBoard);
        } else {
            leaderBoard.setGameSession(gameSession);
        }

        // Persist the leaderboard (if new)
        if (leaderBoard.getLeaderboardId() == null) {
            leaderboardRepository.save(leaderBoard);
        }

        // Set the leaderboard in all entries
        for (LeaderBoardEntry entry : leaderBoard.getEntries()) {
            entry.setLeaderboard(leaderBoard);
            leaderBoardEntryRepository.save(entry);
        }

        // Initialize scores and set the relationship
        Set<Score> scores = new HashSet<>();
        for (User sessionUser : gameSession.getUsers()) {
            Score score = new Score();
            score.setGameSessions(gameSession);
            score.setUser(sessionUser);
            score.setPoints(0); // Initial score
            scores.add(score);
            scoreRepository.save(score);
        }
        gameSession.setScores(scores);

        // Initialize rounds and set the relationship
        Set<Round> rounds = new HashSet<>();
        Round round = new Round();
        round.setGameSessions(gameSession);
        round.setCurrentDrawer(user); // Set the initial drawer
        round.setRound(1); // Initial round number
        rounds.add(round);
        roundRepository.save(round);
        gameSession.setRounds(rounds);

        // Persist the game session and its leaderboards
        GameSession savedgameSession = gameSessionRepository.save(gameSession);

        return savedgameSession;
    }

    @Override
    public void deleteGameSession(Long gameSessionId) {
        gameSessionRepository.deleteById(gameSessionId);
    }


    public void calculateScores(Long sessionId) {
        GameSession gameSession = gameSessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("GameSession not found with id: " + sessionId));

        // Logic to calculate scores
        // Example: Iterate over rounds and calculate scores for each user
        for (Round round : gameSession.getRounds()) {
            for (User user : gameSession.getUsers()) {
                // Custom scoring logic based on game rules
                int scoreValue = calculateScoreForUser(round, user);
                Score score = new Score();
                score.setGameSessions(gameSession);
                score.setUser(user);
                score.setPoints(scoreValue);
                scoreRepository.save(score);
            }
        }
    }

    private int calculateScoreForUser(Round round, User user) {
        // Implement game-specific scoring logic here
        return 0; // Placeholder score calculation
    }
}
