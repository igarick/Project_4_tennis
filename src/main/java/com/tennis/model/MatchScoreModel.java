package com.tennis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchScoreModel {
    private Match match;
    private Score setScore;
    private Score gameScore;
    private Score pointScore;
}
