package com.example.drawscribeio.service;

import com.example.drawscribeio.dto.GameSessionDto;
import com.example.drawscribeio.entity.GameSession;

import java.util.List;

public interface GameSessionService {

    List<GameSession> getAllGameSessions();
    GameSession getGameSessionById(Long gameSessionId);
    GameSession createGameSession(GameSession gameSessionDetails, Long userId);
    GameSessionDto updateGameSession(Long gameSessionId, Long userId);
    void deleteGameSession(Long gameSessionId);

    public GameSessionDto joinOngoingGame(Long gameSessionId, Long userId);
}
