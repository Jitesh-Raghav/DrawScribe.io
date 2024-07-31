package com.example.drawscribeio.service;

import com.example.drawscribeio.entity.GameSession;

import java.util.List;

public interface GameSessionService {

    List<GameSession> getAllGameSessions();
    GameSession getGameSessionById(Long gameSessionId);
    GameSession createGameSession(GameSession gameSessionDetails, Long userId);
    void deleteGameSession(Long gameSessionId);
}
