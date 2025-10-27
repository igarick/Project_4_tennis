package com.tennis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchModel {
    private Integer ID;
    private PlayerModel player1;
    private PlayerModel player2;
    private PlayerModel winner;
    private boolean isTieBreak;
    private boolean isFinished;
}
