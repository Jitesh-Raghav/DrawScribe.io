package com.example.drawscribeio.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="users")       //REFER TO ER DIAGRAMS IF CONFUSION ...
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 250)
    private String avatar;

    @Column(nullable = false)
    private Integer score=0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt= LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt= LocalDateTime.now();

    @ManyToMany
    @JoinTable(name="user_game_sessions",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="game_session_id"))
    private Set<GameSession> gameSessions= new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> chatMessages= new ArrayList<>();
    @PrePersist
    protected void onCreate(){
        createdAt= LocalDateTime.now();
        updatedAt= LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
