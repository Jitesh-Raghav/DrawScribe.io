package com.example.drawscribeio.repository;

import com.example.drawscribeio.entity.Drawing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrawingRepository extends JpaRepository<Drawing, Long> {
}
