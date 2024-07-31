package com.example.drawscribeio.entity;

import com.example.drawscribeio.entity.LeaderBoard.Leaderboard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="game_sessions")
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long game_sessionId;

    @Column(nullable = false, unique = true, length = 250)
    private String session_code;

    @ManyToMany(mappedBy = "gameSessions")
    private Set<User> users= new HashSet<>();

    @OneToMany(mappedBy = "gameSessions")
    private Set<Score> scores = new HashSet<>();

    @OneToMany(mappedBy = "gameSessions")
    private Set<Round> rounds= new HashSet<>();

    @OneToOne(mappedBy="gameSession", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Leaderboard leaderboards;
}
