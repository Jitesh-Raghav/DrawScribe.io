package com.example.drawscribeio.service;

import com.example.drawscribeio.entity.Score;

public interface ScoreService {

    public Score createScore(Long userId, Long gameSessionId, Score score);
}
