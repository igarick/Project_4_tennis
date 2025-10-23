package com.tennis.controller;

import com.tennis.dto.MatchesPaginationDto;
import com.tennis.dto.PageDto;
import com.tennis.dto.PlayerNameDto;
import com.tennis.service.FinishedMatchesPersistenceService;
import com.tennis.service.MatchService;
import com.tennis.service.PaginationService;
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
public class Match extends HttpServlet {
    private static final String MATCHES_JSP = "matches";

    private static final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
    private static final PaginationService paginationService = new PaginationService();
    private static final MatchService matchService = new MatchService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paramFilter = req.getParameter("filter_by_player_name");
        String paramPage = req.getParameter("page");

//        boolean validFormat = RequestValidator.isValidFormat(paramFilter);
//        if (!validFormat) {
//            req.setAttribute("error", RequestValidator.NAME_ERROR);
//        }

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

        long l = Long.parseLong(paramPage);
        int currentPage = Math.toIntExact(l);

        PlayerNameDto nameDto = new PlayerNameDto(paramFilter);
        PageDto currentPageDto = new PageDto(currentPage);

//        } else if (paramFilter != null || !paramFilter.isBlank()) {
//            boolean validFormat = RequestValidator.isValidFormat(paramFilter);
//            if (!validFormat) {
//                req.setAttribute("error", RequestValidator.NAME_ERROR);
//                resp.sendRedirect("matches?page=1");
//            }

//        if (!RequestValidator.isValidFormat(paramFilter)) {
//            req.setAttribute("error", RequestValidator.NAME_ERROR);
//        } else {
        MatchesPaginationDto dto = matchService.determineParamPaginationForMatches(currentPageDto, nameDto);
//        List<MatchDto> matchDtos = dto.matchesDto();
//
//
//        }


//        long l = Long.parseLong(paramPage);
//        int currentPage = Math.toIntExact(l);
//        long amount = 0;
//
//        int offset = (LIMIT * (currentPage - 1));

//        if (paramFilter == null || paramFilter.isBlank()) {
//            matches = finishedMatchesPersistenceService.findAll(offset, LIMIT);
//            amount = paginationService.countMatches();
//        } else if (!RequestValidator.isValidFormat(paramFilter)) {
//            req.setAttribute("error", RequestValidator.NAME_ERROR);
//        } else {
//            PlayerNameDto nameDto = new PlayerNameDto(paramFilter);
//
//            matches = finishedMatchesPersistenceService.findByName(nameDto, offset, LIMIT);
//            amount = paginationService.countPlayersByName(nameDto);
//
//            req.setAttribute("paramFilter", paramFilter);
//        }
//        int totalPages = (int) Math.ceil((double) amount / LIMIT);


//
        req.setAttribute("totalPages", dto.totalPages());
        req.setAttribute("currentPage", dto.currentPage());
        req.setAttribute("matches", dto.matchesDto());
        req.getRequestDispatcher(JspHelper.getPath(MATCHES_JSP)).forward(req, resp);
    }
}
