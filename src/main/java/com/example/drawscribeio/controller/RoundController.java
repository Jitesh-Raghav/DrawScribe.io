package com.example.drawscribeio.controller;

import com.example.drawscribeio.dto.GameSessionDto;
import com.example.drawscribeio.entity.User;
import com.example.drawscribeio.entity.Word;
import com.example.drawscribeio.repository.GameSessionRepository;
import com.example.drawscribeio.service.impl.GameSessionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rounds")
public class RoundController {

    @Autowired
    private GameSessionServiceImpl gameService;
    @Autowired
    private GameSessionRepository gameSessionRepository;

    @PostMapping("/{gameSessionId}/startRound")
    public ResponseEntity<GameSessionDto> startNewRound(@PathVariable Long gameSessionId,
                                                        @RequestParam User drawerId,
                                                        @RequestParam Word wordId) {
        gameService.startNewRound(gameSessionId, drawerId, wordId);
        GameSessionDto gameSessionDto = gameService.getGameSessionDto(gameSessionId); // Fetch updated session
        return ResponseEntity.ok(gameSessionDto);
    }
}

