package com.tennis.util.setter;

import com.tennis.model.MatchScoreModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestAttributeSetter {
    public static void setPlayersAndScore(HttpServletRequest req, MatchScoreModel currentMatch) {
        req.setAttribute("firstPlayerName", currentMatch.getMatchModel().getPlayer1().getName());
        req.setAttribute("firstPlayerId", currentMatch.getMatchModel().getPlayer1().getId());
        req.setAttribute("setScoreFirstPlayer", currentMatch.getFirstPlayerScore().getSets());
        req.setAttribute("gameScoreFirstPlayer", currentMatch.getFirstPlayerScore().getGames());

        req.setAttribute("secondPlayerName", currentMatch.getMatchModel().getPlayer2().getName());
        req.setAttribute("secondPlayerId", currentMatch.getMatchModel().getPlayer2().getId());
        req.setAttribute("setScoreSecondPlayer", currentMatch.getSecondPlayerScore().getSets());
        req.setAttribute("gameScoreSecondPlayer", currentMatch.getSecondPlayerScore().getGames());
    }

    public static void setPointScorePlayers(HttpServletRequest req, MatchScoreModel currentMatch) {
        req.setAttribute("pointScoreFirstPlayer", currentMatch.getFirstPlayerScore().getPoints().displayPoint());
        req.setAttribute("pointScoreSecondPlayer", currentMatch.getSecondPlayerScore().getPoints().displayPoint());
    }

    public static void setTieBreakPointsScorePlayers(HttpServletRequest req, MatchScoreModel currentMatch) {
        req.setAttribute("pointScoreFirstPlayer", currentMatch.getFirstPlayerScore().getTieBreakPoints());
        req.setAttribute("pointScoreSecondPlayer", currentMatch.getSecondPlayerScore().getTieBreakPoints());
    }

    public static void setWinnerName(HttpServletRequest req, MatchScoreModel currentMatch) {
        req.setAttribute("winnerName", currentMatch.getMatchModel().getWinner().getName());
    }

    public static void setUuid(HttpServletRequest req, String uuidParam) {
        req.setAttribute("matchUuid", uuidParam);
    }
}
