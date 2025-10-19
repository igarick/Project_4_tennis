package com.tennis.controller;

import com.tennis.model.MatchScoreModel;
import com.tennis.service.MatchScoreCalculationService;
import com.tennis.service.OngoingMatchesService;
import com.tennis.util.JspHelper;
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
    private static final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidParam = req.getParameter("uuid");
        UUID uuid = UUID.fromString(uuidParam);

        MatchScoreModel currentMatch = ongoingMatchesService.getCurrentMatch(uuid);

        req.setAttribute("pointScoreFirstPlayer", currentMatch.getFirstPlayerScore().getPoints().displayPoint());
        req.setAttribute("pointScoreSecondPlayer", currentMatch.getSecondPlayerScore().getPoints().displayPoint());

        req.setAttribute("firstPlayerName", currentMatch.getMatchModel().getPlayer1().getName());
        req.setAttribute("firstPlayerId", currentMatch.getMatchModel().getPlayer1().getId());
        req.setAttribute("setScoreFirstPlayer", currentMatch.getFirstPlayerScore().getSets());
        req.setAttribute("gameScoreFirstPlayer", currentMatch.getFirstPlayerScore().getGames());

        req.setAttribute("secondPlayerName", currentMatch.getMatchModel().getPlayer2().getName());
        req.setAttribute("secondPlayerId", currentMatch.getMatchModel().getPlayer2().getId());
        req.setAttribute("setScoreSecondPlayer", currentMatch.getSecondPlayerScore().getSets());
        req.setAttribute("gameScoreSecondPlayer", currentMatch.getSecondPlayerScore().getGames());

        req.setAttribute("matchUuid", uuidParam);
        req.getRequestDispatcher(JspHelper.getPath(MATCH_SCORE_JSP)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidParam = req.getParameter("uuid");
        UUID uuid = UUID.fromString(uuidParam);

        MatchScoreModel currentMatch = ongoingMatchesService.getCurrentMatch(uuid);

        String firstPlayerIdParam = req.getParameter("firstPlayerId");
        String secondPlayerIdParam = req.getParameter("secondPlayerId");

        if (firstPlayerIdParam != null) {
            matchScoreCalculationService.calculate(firstPlayerIdParam, currentMatch);
        }

        if (secondPlayerIdParam != null) {
            matchScoreCalculationService.calculate(secondPlayerIdParam, currentMatch);
        }

        boolean isTieBreak = currentMatch.getMatchModel().isTieBreak();

        if (isTieBreak) {
            req.setAttribute("pointScoreFirstPlayer", currentMatch.getFirstPlayerScore().getTieBreakPoints());
            req.setAttribute("pointScoreSecondPlayer", currentMatch.getSecondPlayerScore().getTieBreakPoints());
        } else {
            req.setAttribute("pointScoreFirstPlayer", currentMatch.getFirstPlayerScore().getPoints().displayPoint());
            req.setAttribute("pointScoreSecondPlayer", currentMatch.getSecondPlayerScore().getPoints().displayPoint());
        }

        req.setAttribute("firstPlayerName", currentMatch.getMatchModel().getPlayer1().getName());
        req.setAttribute("firstPlayerId", currentMatch.getMatchModel().getPlayer1().getId());
        req.setAttribute("setScoreFirstPlayer", currentMatch.getFirstPlayerScore().getSets());
        req.setAttribute("gameScoreFirstPlayer", currentMatch.getFirstPlayerScore().getGames());

        req.setAttribute("secondPlayerName", currentMatch.getMatchModel().getPlayer2().getName());
        req.setAttribute("secondPlayerId", currentMatch.getMatchModel().getPlayer2().getId());
        req.setAttribute("setScoreSecondPlayer", currentMatch.getSecondPlayerScore().getSets());
        req.setAttribute("gameScoreSecondPlayer", currentMatch.getSecondPlayerScore().getGames());

        req.setAttribute("matchUuid", uuidParam);

        boolean finished = currentMatch.getMatchModel().isFinished();
        if (finished) {
            req.setAttribute("winnerName", currentMatch.getMatchModel().getWinner().getName());

            ongoingMatchesService.removeCurrentMatch(uuid);

            req.getRequestDispatcher(JspHelper.getPath(MATCH_FINISHED_JSP)).forward(req, resp);
        } else {
            req.getRequestDispatcher(JspHelper.getPath(MATCH_SCORE_JSP)).forward(req, resp);
        }
    }

    /*
    Обработчик POST запросов:

    - Обрабатывает POST запросы к /match-score

    - Через OngoingMatchesService получает экземпляр класса Match для текущего матча,
    который является моделью/частью модели MatchScoreModel

    - Через MatchScoreCalculationService обновляет счёт в матче

    - Если матч закончился - через FinishedMatchesPersistenceService сохраняет законченный матч в базу данных

    - С помощью JSP шаблона отображает MatchScoreModel в виде отрендеренного HTML


    Каждый из упомянутых сервисов делает конкретную работу:
     - OngoingMatchesService хранит текущие матчи и позволяет их записывать/читать
     - MatchScoreCalculationService реализует логику подсчёта счёта матча по очкам/геймам/сетам
     - FinishedMatchesPersistenceService инкапсулирует чтение и запись законченных матчей в БД


*/
}

