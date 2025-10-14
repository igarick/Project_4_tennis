package com.tennis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class MatchScoreModel {
    private Match match;
//    private Score setScore;
//    private Score gameScore;
    private Score firstPlayerScore;
    private Score secondPlayerScore;

//    private int firstPlayerSets;
//    private int secondPlayerSets;
//    private int firstPlayerGames;
//    private int secondPlayerGames;
//    private PointScoreEnum firstPlayerPoints;
//    private PointScoreEnum secondPlayerPoints;
}
