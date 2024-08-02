package com.example.drawscribeio.controller;

import com.example.drawscribeio.dto.GameSessionDto;
import com.example.drawscribeio.entity.GameSession;
import com.example.drawscribeio.service.GameSessionService;
import com.example.drawscribeio.service.impl.GameSessionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game-sessions")
public class GameSessionController {

    @Autowired
    private GameSessionService gameSessionService;

    private GameSessionServiceImpl gameSessionServiceImpl;

    public GameSessionController(GameSessionService gameSessionService) {
        this.gameSessionService= gameSessionService;
    }

    @GetMapping
    private List<GameSession> getAllGameSessions(){
      return gameSessionService.getAllGameSessions();
    }

    @GetMapping("/{gameSessionId}")
    public ResponseEntity<GameSession> getGameSessionById(@PathVariable Long gameSessionId){
        GameSession gameSessionById= gameSessionService.getGameSessionById(gameSessionId);
        if(gameSessionById==null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(gameSessionById, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<GameSession> createGameSession(@RequestBody GameSession gameSession, Long userId){
        return new ResponseEntity<>(gameSessionService.createGameSession(gameSession, userId), HttpStatus.CREATED);
    }

    @PostMapping("/join")
    public ResponseEntity<GameSessionDto> joinOngoingGame(@RequestParam Long gameSessionId, @RequestParam Long userId) {
        GameSessionDto gameSessionDTO = gameSessionService.joinOngoingGame(gameSessionId, userId);
        return ResponseEntity.ok(gameSessionDTO);
    }


    // Endpoint for updating a game session
    @PutMapping("/{gameSessionId}")
    public ResponseEntity<GameSessionDto> updateGameSession(@PathVariable Long gameSessionId, @RequestParam Long userId) {
        GameSessionDto updatedGameSessionDTO = gameSessionService.updateGameSession(gameSessionId, userId);
        return ResponseEntity.ok(updatedGameSessionDTO);
    }


    @DeleteMapping("/{gameSessionId}")
    public ResponseEntity<String> deleteGameSession(@PathVariable Long gameSessionId){
        gameSessionService.deleteGameSession(gameSessionId);
        return new ResponseEntity<>("GameSession deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/{gameSessionId}/startRound")
    public ResponseEntity<GameSessionDto> startRound(@PathVariable Long gameSessionId, @RequestParam Long wordId) {
        GameSessionDto gameSessionDTO = gameSessionServiceImpl.startNewRound(gameSessionId, wordId);
        return ResponseEntity.ok(gameSessionDTO);
    }
}
