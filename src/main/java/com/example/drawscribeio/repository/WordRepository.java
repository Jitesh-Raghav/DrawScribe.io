package com.example.drawscribeio.repository;

import com.example.drawscribeio.entity.Word;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {
    @Query("SELECT w FROM Word w ORDER BY RAND()")
    List<Word> findRandomWords(Pageable pageable);
}

