package com.tennis;

import com.tennis.model.Match;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchScoreModel {
    private Match match;
    private Score set;
    private Score game;
    private Score point;
}
