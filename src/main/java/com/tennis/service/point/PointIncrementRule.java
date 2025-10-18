package com.tennis.service.point;

import com.tennis.model.MatchScoreModel;
import com.tennis.model.PointScoreEnum;
import com.tennis.model.Score;

public class PointIncrementRule {
    private static final int AMOUNT_GAMES_FOR_TIE_BREAK = 6;

    public void incrementPlayerPoint(Score player, Score opponent, MatchScoreModel currentMatch) {
        boolean tieBreak = isTieBreak(player, opponent);
        currentMatch.getMatch().setTieBreak(tieBreak);

        if (tieBreak) {
            incrementTieBreakPoint(player);
        } else {
            incrementMatchPoint(player);
        }
    }

    private boolean isTieBreak(Score player, Score opponent) {
        return (player.getGames() == AMOUNT_GAMES_FOR_TIE_BREAK &&
                opponent.getGames() == AMOUNT_GAMES_FOR_TIE_BREAK);
    }

    private void incrementTieBreakPoint(Score player) {
        int updatedTieBreakPoints = player.getTieBreakPoints() + 1;
        player.setTieBreakPoints(updatedTieBreakPoints);
    }

    private void incrementMatchPoint(Score player) {
        PointScoreEnum point = player.getPoints();
        switch (point) {
            case LOVE:
                player.setPoints(PointScoreEnum.FIFTEEN);
                break;
            case FIFTEEN:
                player.setPoints(PointScoreEnum.THIRTY);
                break;
            case THIRTY:
                player.setPoints(PointScoreEnum.FORTY);
                break;
            case FORTY:
                player.setPoints(PointScoreEnum.ADVANTAGE);
                break;
            case ADVANTAGE:
                player.setPoints(PointScoreEnum.WIN);
                break;
            default:
                player.setPoints(point);
        }
    }
}
