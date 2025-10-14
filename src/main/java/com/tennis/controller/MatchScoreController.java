package com.tennis.controller;

import com.tennis.model.MatchScoreModel;
import com.tennis.service.MatchScoreCalculationService;
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
    private static final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uuidParam = req.getParameter("uuid");
        UUID uuid = UUID.fromString(uuidParam);

        MatchScoreModel currentMatch = ongoingMatchesService.getCurrentMatch(uuid);

        req.setAttribute("firstPlayerName", currentMatch.getMatch().getPlayer1().getName());
        req.setAttribute("firstPlayerId", currentMatch.getMatch().getPlayer1().getId());
        req.setAttribute("setScoreFirstPlayer", currentMatch.getFirstPlayerScore().getSets());
        req.setAttribute("gameScoreFirstPlayer", currentMatch.getFirstPlayerScore().getGames());
        req.setAttribute("pointScoreFirstPlayer", currentMatch.getFirstPlayerScore().getPoints().displayPoint());



//        req.setAttribute("setScoreFirstPlayer", currentMatch.getFirstPlayerSets());
//        req.setAttribute("gameScoreFirstPlayer", currentMatch.getFirstPlayerGames());
//        req.setAttribute("pointScoreFirstPlayer", currentMatch.getFirstPlayerPoints().displayPoint());

        req.setAttribute("secondPlayerName", currentMatch.getMatch().getPlayer2().getName());
        req.setAttribute("secondPlayerId", currentMatch.getMatch().getPlayer2().getId());
        req.setAttribute("setScoreSecondPlayer", currentMatch.getSecondPlayerScore().getSets());
        req.setAttribute("gameScoreSecondPlayer", currentMatch.getSecondPlayerScore().getGames());
        req.setAttribute("pointScoreSecondPlayer", currentMatch.getSecondPlayerScore().getPoints().displayPoint());


//        req.setAttribute("setScoreSecondPlayer", currentMatch.getSecondPlayerSets());
//        req.setAttribute("gameScoreSecondPlayer", currentMatch.getSecondPlayerGames());
//        req.setAttribute("pointScoreSecondPlayer", currentMatch.getSecondPlayerPoints().displayPoint());

        req.setAttribute("matchUuid", uuidParam);

        req.getRequestDispatcher("match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidParam = req.getParameter("uuid");
        UUID uuid = UUID.fromString(uuidParam);

        MatchScoreModel currentMatch = ongoingMatchesService.getCurrentMatch(uuid);

        String firstPlayerIdParam = req.getParameter("firstPlayerId");
        String secondPlayerIdParam = req.getParameter("secondPlayerId");

        if (firstPlayerIdParam != null) {
            matchScoreCalculationService.updateScore(firstPlayerIdParam, currentMatch);
        }

        if (secondPlayerIdParam != null) {
            matchScoreCalculationService.updateScore(secondPlayerIdParam, currentMatch);
        }

        req.setAttribute("firstPlayerName", currentMatch.getMatch().getPlayer1().getName());
        req.setAttribute("firstPlayerId", currentMatch.getMatch().getPlayer1().getId());
        req.setAttribute("setScoreFirstPlayer", currentMatch.getFirstPlayerScore().getSets());
        req.setAttribute("gameScoreFirstPlayer", currentMatch.getFirstPlayerScore().getGames());
        req.setAttribute("pointScoreFirstPlayer", currentMatch.getFirstPlayerScore().getPoints().displayPoint());


//        req.setAttribute("setScoreFirstPlayer", currentMatch.getFirstPlayerSets());
//        req.setAttribute("gameScoreFirstPlayer", currentMatch.getFirstPlayerGames());
//        req.setAttribute("pointScoreFirstPlayer", currentMatch.getFirstPlayerPoints().displayPoint());

        req.setAttribute("secondPlayerName", currentMatch.getMatch().getPlayer2().getName());
        req.setAttribute("secondPlayerId", currentMatch.getMatch().getPlayer2().getId());
        req.setAttribute("setScoreSecondPlayer", currentMatch.getSecondPlayerScore().getSets());
        req.setAttribute("gameScoreSecondPlayer", currentMatch.getSecondPlayerScore().getGames());
        req.setAttribute("pointScoreSecondPlayer", currentMatch.getSecondPlayerScore().getPoints().displayPoint());

//        req.setAttribute("setScoreSecondPlayer", currentMatch.getSecondPlayerSets());
//        req.setAttribute("gameScoreSecondPlayer", currentMatch.getSecondPlayerGames());
//        req.setAttribute("pointScoreSecondPlayer", currentMatch.getSecondPlayerPoints().displayPoint());

        System.out.println(uuidParam);

        System.out.println(req.getParameter("firstPlayerId"));
        System.out.println(req.getParameter("secondPlayerId"));


        req.setAttribute("matchUuid", uuidParam);
        req.getRequestDispatcher("match-score.jsp").forward(req, resp);

//
//        MatchScoreModel currentMatch = ongoingMatchesService.getCurrentMatch(uuid);
//        req.setAttribute("firstPlayerName", currentMatch.getMatch().getPlayer1().getName());
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

