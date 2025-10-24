package com.tennis.controller;

import com.tennis.model.*;
import com.tennis.service.NewMatchService;
import com.tennis.service.OngoingMatchesService;
import com.tennis.util.JspHelper;
import com.tennis.validator.RequestValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchController extends HttpServlet {
    private static final String NEW_MATCH_JSP = "new-match";
    private static final String MATCH_SCORE_PATH = "match-score?uuid=";

    private static final NewMatchService newMatchService = new NewMatchService();
    private static final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath(NEW_MATCH_JSP)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter1 = req.getParameter("playerOne");
        String parameter2 = req.getParameter("playerTwo");

        Optional<String> errorMessage = RequestValidator.validateParamAndGetErrorMessage(parameter1, parameter2);
        if (errorMessage.isPresent()) {
            req.setAttribute("error", errorMessage.get());
            req.getRequestDispatcher(JspHelper.getPath(NEW_MATCH_JSP)).forward(req, resp);
            return;
        }

        PlayerModel firstPlayerModel = new PlayerModel(null, parameter1);
        PlayerModel secondPlayerModel = new PlayerModel(null, parameter2);

        PlayerModel firstPlayer = newMatchService.get(firstPlayerModel);
        PlayerModel secondPlayer = newMatchService.get(secondPlayerModel);

        UUID uuid = UUID.randomUUID();

        MatchScoreModel matchScoreModel = new MatchScoreModel(
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
        ongoingMatchesService.writeCurrentMatch(uuid, matchScoreModel);

        resp.sendRedirect(MATCH_SCORE_PATH + uuid);
    }
}

