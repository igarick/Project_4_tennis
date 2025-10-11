package com.tennis.controller;

import com.tennis.model.MatchScoreModel;
import com.tennis.service.OngoingMatchesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreController extends HttpServlet {
    private static final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uuidParam = req.getParameter("uuid");
        UUID uuid = UUID.fromString(uuidParam);

        MatchScoreModel currentMatch = ongoingMatchesService.getCurrentMatch(uuid);
        req.setAttribute("firstPlayerName", currentMatch.getMatch().getPlayer1().getName());
        req.setAttribute("setScoreFirstPlayer", currentMatch.getSetScore().getFirstPlayer());
        req.setAttribute("gameScoreFirstPlayer", currentMatch.getGameScore().getFirstPlayer());
        req.setAttribute("pointScoreFirstPlayer", currentMatch.getPointScore().getFirstPlayer());

        req.setAttribute("secondPlayerName", currentMatch.getMatch().getPlayer2().getName());
        req.setAttribute("setScoreSecondPlayer", currentMatch.getSetScore().getSecondPlayer());
        req.setAttribute("gameScoreSecondPlayer", currentMatch.getGameScore().getSecondPlayer());
        req.setAttribute("pointScoreSecondPlayer", currentMatch.getPointScore().getSecondPlayer());

        req.getRequestDispatcher("match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String uuidParam = req.getParameter("uuid");
//        UUID uuid = UUID.fromString(uuidParam);
//
//        MatchScoreModel currentMatch = ongoingMatchesService.getCurrentMatch(uuid);
//        req.setAttribute("firstPlayerName", currentMatch.getMatch().getPlayer1().getName());
    }

    /*
    Обрабатывает POST запросы к /match-score

    Через OngoingMatchesService получает экземпляр класса Match для текущего матча,
    который является моделью/частью модели MatchScoreModel


*/
}

