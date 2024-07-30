package com.example.drawscribeio.entity;

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
    private Long game_session_id;

    @Column(nullable = false, unique = true, length = 10)
    private String session_code;

    @ManyToMany(mappedBy = "gameSessions")
    private Set<User> users= new HashSet<>();

}
