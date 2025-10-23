package com.tennis.controller;

import com.tennis.dto.MatchesPaginationDto;
import com.tennis.dto.PageDto;
import com.tennis.dto.PlayerNameDto;
import com.tennis.dto.RequestMatchParamsDto;
import com.tennis.service.MatchService;
import com.tennis.util.JspHelper;
import com.tennis.validator.MatchParamFilterValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/matches")
public class MatchesController extends HttpServlet {
    private static final String MATCHES_JSP = "matches";

    private static final MatchService matchService = new MatchService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paramFilter = req.getParameter("filter_by_player_name");
        String paramPage = req.getParameter("page");

        if (paramPage == null) {
            if (paramFilter == null || paramFilter.isBlank()) {
                resp.sendRedirect("matches?page=1");

            } else {
                MatchParamFilterValidator.validate(paramFilter);
                resp.sendRedirect("matches?page=1&filter_by_player_name="
                                  + URLEncoder.encode(paramFilter, StandardCharsets.UTF_8));
            }
            return;
        }
        RequestMatchParamsDto paramsDto = new RequestMatchParamsDto(
                paramPage,
                paramFilter);

        MatchesPaginationDto dto = matchService.determineParamPaginationForMatches(paramsDto);

        req.setAttribute("paramFilter", paramFilter);
        req.setAttribute("totalPages", dto.totalPages());
        req.setAttribute("currentPage", dto.currentPage());
        req.setAttribute("matches", dto.matchesDto());
        req.getRequestDispatcher(JspHelper.getPath(MATCHES_JSP)).forward(req, resp);
    }
}
