package com.tennis.entity;

import com.tennis.model.MatchScoreModel;
import com.tennis.util.EntitiesMapperAndBuilder;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public static Match from(MatchScoreModel model) {
        return Match.builder()
                .player1(EntitiesMapperAndBuilder.mapToEntity(model.getMatchModel().getPlayer1()))
                .player2(EntitiesMapperAndBuilder.mapToEntity(model.getMatchModel().getPlayer2()))
                .winner(EntitiesMapperAndBuilder.mapToEntity(model.getMatchModel().getWinner()))
                .build();
    }
}
