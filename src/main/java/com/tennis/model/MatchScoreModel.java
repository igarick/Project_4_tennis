package com.tennis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class MatchScoreModel {
    private Match match;
    private Score firstPlayerScore;
    private Score secondPlayerScore;

    private boolean tieBreak;
}
