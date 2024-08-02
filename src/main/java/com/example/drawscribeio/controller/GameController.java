package com.example.drawscribeio.controller;

import com.example.drawscribeio.dto.WordDto;
import com.example.drawscribeio.service.GameSessionService;
import com.example.drawscribeio.service.impl.GameSessionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameSessionServiceImpl gameSessionService;

    @GetMapping("/{gameSessionId}/words")
    public ResponseEntity<List<WordDto>> getThreeRandomWordsForGameSession(@PathVariable Long gameSessionId) {
        List<WordDto> randomWords = gameSessionService.getThreeRandomWords();
        return ResponseEntity.ok(randomWords);
    }
}
