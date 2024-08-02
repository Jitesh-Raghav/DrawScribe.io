package com.example.drawscribeio.service.impl;

import com.example.drawscribeio.entity.Word;
import com.example.drawscribeio.repository.WordRepository;
import com.example.drawscribeio.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WordServiceImpl implements WordService {

    @Autowired
    private WordRepository wordRepository;
    @Override
    public List<Word> getRandomWords(int count) {
        return wordRepository.findRandomWords(PageRequest.of(0, count));
    }
}
