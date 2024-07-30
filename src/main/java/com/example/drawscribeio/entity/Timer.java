package com.example.drawscribeio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
@Entity
@Table(name="timer")
public class Timer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long timerId;

    @Column(nullable = false)
    private LocalDateTime startTime= LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime endTime= LocalDateTime.now();

    @Column(nullable = false)
    private Long duration;

    @JoinColumn(name="round_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Round round;
}
