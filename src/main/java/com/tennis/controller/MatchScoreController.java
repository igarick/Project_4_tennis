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
        req.getRequestDispatcher("match-score.jsp").forward(req, resp);

        String strUuid = req.getParameter("uuid");
        System.out.println(strUuid);

        UUID uuid = UUID.fromString(strUuid);
        MatchScoreModel one = ongoingMatchesService.getCurrentMatch(uuid);
        System.out.println(one);

    }
    /*
    Обрабатывает POST запросы к /match-score

    Через OngoingMatchesService получает экземпляр класса Match для текущего матча,
    который является моделью/частью модели MatchScoreModel


*/
}

