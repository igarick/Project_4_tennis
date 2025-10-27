package com.tennis.controller;

import com.tennis.dto.PaginatedMatchesDto;
import com.tennis.dto.RequestMatchParamsDto;
import com.tennis.service.MatchService;
import com.tennis.util.JspHelper;
import com.tennis.util.setter.RequestPaginationSetter;
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
    private static final String PATH_TO_FIRST_PAGE = "matches?page=1";
    private static final String PATH_TO_FIRST_PAGE_WITH_FILTER = "matches?page=1&filter_by_player_name=";

    private MatchService matchService;

    @Override
    public void init() {
        this.matchService = (MatchService) getServletContext().getAttribute("matchService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paramFilter = req.getParameter("filter_by_player_name");
        String paramPage = req.getParameter("page");

        if (paramPage == null) {
            if (paramFilter == null || paramFilter.isBlank()) {
                resp.sendRedirect(PATH_TO_FIRST_PAGE);

            } else {
                MatchParamFilterValidator.validate(paramFilter);
                resp.sendRedirect(PATH_TO_FIRST_PAGE_WITH_FILTER
                                  + URLEncoder.encode(paramFilter.toUpperCase(), StandardCharsets.UTF_8));
            }
            return;
        }
        RequestMatchParamsDto paramsDto = new RequestMatchParamsDto(
                paramPage,
                paramFilter);

        PaginatedMatchesDto paginatedMatchesDto = matchService.getPaginatedMatches(paramsDto);

        RequestPaginationSetter.SetAttributes(req, paramFilter, paginatedMatchesDto);
        req.getRequestDispatcher(JspHelper.getPath(MATCHES_JSP)).forward(req, resp);
    }
}
