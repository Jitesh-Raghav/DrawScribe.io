package com.example.drawscribeio.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
public class Round implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long round_id;

    @Column(nullable = false)
    private LocalDateTime roundStartTime;
    @Column
    private LocalDateTime roundEndTime;

    @Column(nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_session_id", nullable = false)
    private GameSession gameSessions;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="current_drawer_id", nullable = false)
    private User currentDrawer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="word_id", nullable = false)
    private Word drawingWord;

    @OneToMany(mappedBy = "round",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Timer> timers= new HashSet<>();

    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Drawing> drawingSet= new HashSet<>();
}
