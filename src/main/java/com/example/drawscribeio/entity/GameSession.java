package com.example.drawscribeio.entity;

import com.example.drawscribeio.dto.UserDto;
import com.example.drawscribeio.entity.LeaderBoard.Leaderboard;
import com.example.drawscribeio.repository.GameSessionRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="game_sessions")
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long game_sessionId;

    @Column(nullable = false, unique = true, length = 250)
    private String session_code;

    @ManyToMany(mappedBy = "gameSessions")
    private List<User> users= new ArrayList<>();

    @OneToMany(mappedBy = "gameSessions")
    private Set<Score> scores = new HashSet<>();

    @OneToMany(mappedBy = "gameSessions")
    private List<Round> rounds= new ArrayList<>();

    @OneToOne(mappedBy="gameSession", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Leaderboard leaderboards;

    private int currentRound = 1;
    private int currentCycle = 1;
    private int currentPlayerIndex = 0; // Index of the current player in the list

    // ... other fields and methods

    private boolean isEnded = false; // New field to indicate if the game has ended

    public User getCurrentPlayer() {
        return users.get(currentPlayerIndex);
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % users.size();
        if (currentPlayerIndex == 0) {
            currentCycle++;
            if (currentCycle > 3) { // Assuming 3 cycles
                endGame();
            }
        }
    }

    private void endGame() {
//        this.isEnded = true;
//
//        // Calculate final scores and determine the winner(s)
//        // Assuming a method calculateFinalScores exists
//        List<Score> finalScores = calculateFinalScores();
//
//        // Notify players (this could be via WebSocket, email, etc.)
//        notifyPlayers(finalScores);
//
//        // Persist changes
//        gameSessionRepository.save(this);
    }

    private List<Score> calculateFinalScores() {
        // Implement logic to calculate final scores
        return new ArrayList<>(scores);
    }

    private void notifyPlayers(List<Score> finalScores) {
        // Implement logic to notify players about the end of the game and the final scores
        // This could be via WebSocket, email, push notification, etc.
    }

    public String getCurrentWord() {
        if (rounds != null && !rounds.isEmpty()) {
            // Assuming the current round is the last one in the list
            return rounds.get(rounds.size() - 1).getCurrentWord();
        }
        return null;
    }

    public int getCorrectGuesses() {
        // Implement logic to count correct guesses
        return (int) scores.stream().filter(score -> score.isCorrectGuess()).count();
    }
}
