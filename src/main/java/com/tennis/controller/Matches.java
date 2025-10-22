package com.tennis.controller;

import com.tennis.dto.MatchDto;
import com.tennis.dto.PlayerNameDto;
import com.tennis.service.FinishedMatchesPersistenceService;
import com.tennis.service.PaginationService;
import com.tennis.util.JspHelper;
import com.tennis.validator.RequestValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/matches")
public class Matches extends HttpServlet {
    private static final String MATCHES_JSP = "matches";

    private static final String NAME_ERROR = "The name must be 1 - 15 English letters";

    private static final int LIMIT = 3;

    private static final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
    private static final PaginationService paginationService = new PaginationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paramFilter = req.getParameter("filter_by_player_name");
        String paramPage = req.getParameter("page");

        List<MatchDto> matches = List.of();

        if (paramPage == null) {
            if (paramFilter == null || paramFilter.isBlank()) {
                resp.sendRedirect("matches?page=1");
            } else {
                resp.sendRedirect("matches?page=1&filter_by_player_name="
                                  + URLEncoder.encode(paramFilter, StandardCharsets.UTF_8));
            }
            return;
        }

        long l = Long.parseLong(paramPage);
        int currentPage = Math.toIntExact(l);
        long amount = 0;

        int offset = (LIMIT * (currentPage - 1));

        if (paramFilter == null || paramFilter.isBlank()) {
            matches = finishedMatchesPersistenceService.findAll(offset, LIMIT);
            amount = paginationService.countMatches();
        } else if (!RequestValidator.isValidFormat(paramFilter)) {
            req.setAttribute("error", NAME_ERROR);
        } else {
            PlayerNameDto nameDto = new PlayerNameDto(paramFilter);

            matches = finishedMatchesPersistenceService.findByName(nameDto, offset, LIMIT);
            amount = paginationService.countPlayersByName(nameDto);

            req.setAttribute("paramFilter", paramFilter);
        }
        int totalPages = (int) Math.ceil((double) amount / LIMIT);

        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("matches", matches);
        req.getRequestDispatcher(JspHelper.getPath(MATCHES_JSP)).forward(req, resp);
    }
}
