package com.tennis.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchScoreModel {
    private MatchModel matchModel;
    private Score firstPlayerScore;
    private Score secondPlayerScore;
}
