package com.tennis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MatchScoreModel {
    private MatchModel matchModel;
    private Score firstPlayerScore;
    private Score secondPlayerScore;

    public static MatchScoreModel createInitial(PlayerModel firstPlayer, PlayerModel secondPlayer) {
        return MatchScoreModel.builder()
                .matchModel(new MatchModel(
                        null,
                        firstPlayer,
                        secondPlayer,
                        null,
                        false,
                        false
                ))
                .firstPlayerScore(new Score(0, 0, PointScoreEnum.LOVE, 0))
                .secondPlayerScore(new Score(0, 0, PointScoreEnum.LOVE, 0))
                .build();
    }
}
