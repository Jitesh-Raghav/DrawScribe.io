package com.example.drawscribeio.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScoreDto {

    private Long scoreId;
    private Integer points;
    private boolean isCorrectGuess;
    private Long userId;
    private Long gameSessionId;
}
