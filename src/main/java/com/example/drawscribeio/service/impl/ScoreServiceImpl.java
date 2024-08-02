package com.example.drawscribeio.service.impl;

import com.example.drawscribeio.entity.GameSession;
import com.example.drawscribeio.entity.Score;
import com.example.drawscribeio.entity.User;
import com.example.drawscribeio.repository.GameSessionRepository;
import com.example.drawscribeio.repository.RoundRepository;
import com.example.drawscribeio.repository.ScoreRepository;
import com.example.drawscribeio.repository.UserRepository;
import com.example.drawscribeio.service.ScoreService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameSessionRepository gameSessionRepository;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private RoundRepository roundRepository;

    private static final int BASE_POINTS = 100;
    private static final int BONUS_POINTS = 50;
    private static final int TIME_FACTOR = 10;

    public int calculateGuessPoints(int guessTime, int roundDuration, int guessOrder) {
        int timeBasedBonus = BONUS_POINTS - (guessTime * BONUS_POINTS / roundDuration);
        int orderBasedPoints = BASE_POINTS - (guessOrder * (BASE_POINTS / 10));
        return Math.max(orderBasedPoints + timeBasedBonus, 0);
    }

    public int calculateDrawerPoints(int correctGuesses) {
        return correctGuesses * BASE_POINTS;
    }

    public void handleCorrectGuess(Long guesserId, Long drawerId, String guessedWord, int guessTime, int roundDuration, int guessOrder, Long gameSessionId) {
        User guesser = userRepository.findById(guesserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + guesserId));
        User drawer = userRepository.findById(drawerId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + drawerId));
        GameSession gameSession = gameSessionRepository.findById(gameSessionId)
                .orElseThrow(() -> new EntityNotFoundException("GameSession not found with id: " + gameSessionId));

        // Retrieve the current word from the game session
        String currentWord = gameSession.getCurrentWord();
        boolean isCorrectGuess = currentWord != null && currentWord.equalsIgnoreCase(guessedWord);

        if (isCorrectGuess) {
            // Calculate points for the guesser
            long guesserPoints = (long) calculateGuessPoints(guessTime, roundDuration, guessOrder);

            // Create and save the guesser's score
            Score guesserScore = new Score();
            guesserScore.setPoints((int) guesserPoints);
            guesserScore.setCorrectGuess(true); // It is indeed a correct guess
            createScore(guesser.getUserId(), gameSession.getGame_sessionId(), guesserScore);
        }

        // Update the drawer's score
        int correctGuesses = gameSession.getCorrectGuesses(); // logic to get the number of correct guesses
        int drawerPoints = calculateDrawerPoints(correctGuesses);

        // Create and save the drawer's score
        Score drawerScore = new Score();
        drawerScore.setPoints(drawerPoints);
        drawerScore.setCorrectGuess(false); // Drawer is not guessing
        createScore(drawer.getUserId(), gameSession.getGame_sessionId(), drawerScore);
    }

    @Override
    public Score createScore(Long userId, Long gameSessionId, Score score) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        GameSession gameSession = gameSessionRepository.findById(gameSessionId)
                .orElseThrow(() -> new EntityNotFoundException("GameSession not found with id: " + gameSessionId));

        score.setUser(user);
        score.setGameSessions(gameSession);

        return scoreRepository.save(score);
    }
}
