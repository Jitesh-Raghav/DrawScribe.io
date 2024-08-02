package com.example.drawscribeio.dto.Mapper;

import com.example.drawscribeio.dto.RoundDto;
import com.example.drawscribeio.entity.Round;

public class RoundMapper {
    public static RoundDto toRoundDTO(Round round) {
        RoundDto roundDTO = new RoundDto();
        roundDTO.setRoundId(round.getRound_id());
        roundDTO.setRoundNumber(round.getRoundNumber());
        roundDTO.setCurrentDrawer(UserMapper.toUserDTO(round.getCurrentDrawer()));
        roundDTO.setDrawingWord(round.getDrawingWord());
        // Add other fields as necessary
        return roundDTO;
    }
}
