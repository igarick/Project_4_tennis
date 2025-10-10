package com.tennis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameModel {
    private ScorePlayerModel player1;
    private ScorePlayerModel player2;
}
