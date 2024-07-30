package com.example.drawscribeio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="words")
public class Word implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long word_id;

    @Column(nullable = false, unique = true)
    private String word;

    @OneToMany(mappedBy = "drawingWord")
    private Set<Round> rounds= new HashSet<>();
}
