package com.example.drawscribeio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
@Table(name="scores")
public class Score implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long score_id;

    @Column(nullable = false)
    private Integer points;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="game_session_id", nullable = false)
    private GameSession gameSessions;

    @ManyToOne
    @JoinColumn(name="round_id", nullable = false)
    private Round round;

}
