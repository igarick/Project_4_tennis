package com.tennis.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Score {
    private int sets;
    private int games;
    private PointScoreEnum points;
    private int tieBreakPoints;
}
