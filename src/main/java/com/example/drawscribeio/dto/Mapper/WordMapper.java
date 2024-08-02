package com.example.drawscribeio.dto.Mapper;

import com.example.drawscribeio.dto.WordDto;
import com.example.drawscribeio.entity.Word;

public class WordMapper {
    public static WordDto toWordDTO(Word word) {
        WordDto wordDTO = new WordDto();
        wordDTO.setWordId(word.getWord_id());
        wordDTO.setWord(word.getWord());
        return wordDTO;
    }
}
