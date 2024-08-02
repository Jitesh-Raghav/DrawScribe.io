package com.example.drawscribeio.dto.Mapper;

import com.example.drawscribeio.dto.ScoreDto;
import com.example.drawscribeio.entity.Score;

public class ScoreMapper {
    public static ScoreDto toScoreDTO(Score score) {
        ScoreDto scoreDTO = new ScoreDto();
        scoreDTO.setScoreId(score.getScore_id());
        scoreDTO.setPoints(score.getPoints());
        scoreDTO.setCorrectGuess(score.isCorrectGuess());
        // Add other fields as necessary
        return scoreDTO;
    }
}
