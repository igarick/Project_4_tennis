package com.tennis.controller;

import com.tennis.model.MatchScoreModel;
import com.tennis.service.MatchScoreCalculationService;
import com.tennis.service.MatchScoreService;
import com.tennis.service.OngoingMatchesService;
import com.tennis.service.point.PointIncrementRule;
import com.tennis.util.JspHelper;
import com.tennis.util.RequestAttributeSetter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreController extends HttpServlet {
    private static final String MATCH_SCORE_JSP = "match-score";
    private static final String MATCH_FINISHED_JSP = "match-finished";

    private static final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();

    private static final MatchScoreService matchScoreService = new MatchScoreService(
            new MatchScoreCalculationService(
                    new PointIncrementRule()
            )
    );

//    private static final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService(
//            new PointIncrementRule()
//    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidParam = req.getParameter("uuid");
        UUID uuid = UUID.fromString(uuidParam);

        MatchScoreModel currentMatch = ongoingMatchesService.getCurrentMatch(uuid);

        RequestAttributeSetter.setPlayersAndScore(req, currentMatch);
        RequestAttributeSetter.setPointScorePlayers(req, currentMatch);
        RequestAttributeSetter.setUuid(req, uuidParam);
        req.getRequestDispatcher(JspHelper.getPath(MATCH_SCORE_JSP)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidParam = req.getParameter("uuid");
        UUID uuid = UUID.fromString(uuidParam);

        MatchScoreModel currentMatch = ongoingMatchesService.getCurrentMatch(uuid);

        String firstPlayerIdParam = req.getParameter("firstPlayerId");
        String secondPlayerIdParam = req.getParameter("secondPlayerId");

        matchScoreService.updateCurrentPlayerScore(firstPlayerIdParam, secondPlayerIdParam, currentMatch);

        boolean isTieBreak = currentMatch.getMatchModel().isTieBreak();
        if (isTieBreak) {
            RequestAttributeSetter.setTieBreakPointsScorePlayers(req, currentMatch);
        } else {
            RequestAttributeSetter.setPointScorePlayers(req, currentMatch);
        }
        RequestAttributeSetter.setPlayersAndScore(req, currentMatch);
        RequestAttributeSetter.setUuid(req, uuidParam);

        boolean finished = currentMatch.getMatchModel().isFinished();
        if (finished) {
            RequestAttributeSetter.setWinnerName(req, currentMatch);

            ongoingMatchesService.removeCurrentMatch(uuid);

            req.getRequestDispatcher(JspHelper.getPath(MATCH_FINISHED_JSP)).forward(req, resp);
        } else {
            req.getRequestDispatcher(JspHelper.getPath(MATCH_SCORE_JSP)).forward(req, resp);
        }
    }
}

