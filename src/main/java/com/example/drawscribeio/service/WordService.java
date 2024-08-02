package com.example.drawscribeio.service;

import com.example.drawscribeio.entity.Word;

import java.util.List;

public interface WordService {

    public List<Word> getRandomWords(int count);
}
