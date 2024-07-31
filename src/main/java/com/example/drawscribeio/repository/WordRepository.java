package com.example.drawscribeio.repository;

import com.example.drawscribeio.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
}
