package com.tennis.model;

import com.tennis.model.Player;
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
    @Column(nullable = false)
    private Integer ID;
    @ManyToOne
    @JoinColumn(name = "player_1_id")
    private Player player1;
    @ManyToOne
    @JoinColumn(name = "player_2_id")
    private Player player2;
    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Player winner;
}
