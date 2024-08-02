package com.example.drawscribeio.controller;

import com.example.drawscribeio.dto.ScoreDto;
import com.example.drawscribeio.entity.Score;
import com.example.drawscribeio.service.ScoreService;
import com.example.drawscribeio.service.impl.ScoreServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private ScoreServiceImpl scoreServiceImpl;

    @PostMapping
    public ResponseEntity<ScoreDto> createScore(@RequestBody ScoreDto scoreDto) {
        // Convert DTO to entity
        Score score = new Score();
        score.setPoints(scoreDto.getPoints());

        // Call the service to save the entity
        Score savedScore = scoreService.createScore(scoreDto.getUserId(), scoreDto.getGameSessionId(),score);

        // Convert the saved entity back to DTO
        ScoreDto savedScoreDTO = new ScoreDto();
        savedScoreDTO.setScoreId(savedScore.getScore_id());
        savedScoreDTO.setPoints(savedScore.getPoints());
        savedScoreDTO.setUserId(savedScore.getUser().getUserId());
        savedScoreDTO.setCorrectGuess(savedScoreDTO.isCorrectGuess());
        savedScoreDTO.setGameSessionId(savedScore.getGameSessions().getGame_sessionId());

        return new ResponseEntity<>(savedScoreDTO, HttpStatus.CREATED);
    }

    @PostMapping("/handle-correct-guess")
    public ResponseEntity<String> handleCorrectGuess(@RequestParam Long guesserId,
                                                    @RequestParam Long drawerId,
                                                    @RequestParam String guessedWord,
                                                    @RequestParam int guessTime,
                                                    @RequestParam int roundDuration,
                                                    @RequestParam int guessOrder,
                                                   @PathVariable Long gameSessionId){
        try {
            scoreServiceImpl.handleCorrectGuess(
                    guesserId,
                    drawerId,
                    guessedWord,
                    guessTime,
                    roundDuration,
                    guessOrder,
                    gameSessionId
            );
            return ResponseEntity.ok("Score updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

}
