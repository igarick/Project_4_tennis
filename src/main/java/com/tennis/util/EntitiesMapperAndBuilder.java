package com.tennis.util;

import com.tennis.entity.Match;
import com.tennis.entity.Player;
import com.tennis.model.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EntitiesMapperAndBuilder {
    public static MatchScoreModel buildInitialMatchScore(PlayerModel firstPlayer, PlayerModel secondPlayer) {
        return new MatchScoreModel(
                new MatchModel(
                        null,
                        firstPlayer,
                        secondPlayer,
                        null,
                        false,
                        false
                ),
                new Score(0,
                        0,
                        PointScoreEnum.LOVE,
                        0),
                new Score(0,
                        0,
                        PointScoreEnum.LOVE,
                        0)
        );
    }

    public static Match buildMatch(MatchScoreModel currentMatch) {
        return new Match(
                null,
                new Player(
                        currentMatch.getMatchModel().getPlayer1().getId(),
                        currentMatch.getMatchModel().getPlayer1().getName()
                ),
                new Player(
                        currentMatch.getMatchModel().getPlayer2().getId(),
                        currentMatch.getMatchModel().getPlayer2().getName()
                ),
                new Player(
                        currentMatch.getMatchModel().getWinner().getId(),
                        currentMatch.getMatchModel().getWinner().getName()
                )
        );
    }
}
