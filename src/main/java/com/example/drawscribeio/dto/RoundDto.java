package com.example.drawscribeio.dto;

import com.example.drawscribeio.entity.Word;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class RoundDto {
    private Long roundId;
    private int roundNumber;
    private Long currentDrawerId;
    private Long gameSessionId;
    private boolean isCurrentRound;
    private Long drawingWordId;
    private UserDto currentDrawer;
    private Word drawingWord;



}
