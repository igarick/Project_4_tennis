package com.tennis.controller;

import com.tennis.dto.MatchDto;
import com.tennis.service.FinishedMatchesPersistenceService;
import com.tennis.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class Matches extends HttpServlet {
    private static final String MATCHES_JSP = "matches";

    private static final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String paramFilter = req.getParameter("filter_by_player_name");

        // валидация имени -----!!!!!!

        if (paramFilter == null) {
            List<MatchDto> matches = finishedMatchesPersistenceService.findAll();
            req.setAttribute("matches", matches);
        } else {
            List<MatchDto> matchesByName = finishedMatchesPersistenceService.findByName(paramFilter);
            req.setAttribute("matches", matchesByName);
        }

        req.getRequestDispatcher(JspHelper.getPath(MATCHES_JSP)).forward(req, resp);
    }

}
