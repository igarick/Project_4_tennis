package com.tennis.model;

import com.tennis.entity.Player;
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
    private Player player1;
    private Player player2;
    private Player winner;
    private boolean isTieBreak;
    private boolean isFinished;
}
