package com.tennis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @ManyToOne
    @JoinColumn(name = "PLAYER1")
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "PLAYER2")
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "WINNER")
    private Player winner;
}
