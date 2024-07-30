package com.example.drawscribeio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor @NoArgsConstructor
@Entity@Table(name="drawings")
public class Drawing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long drawingId;

    @Lob
    @Column(nullable = false)
    private byte[] imageData;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id", nullable = false)
    private Round round;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User drawer;
}
