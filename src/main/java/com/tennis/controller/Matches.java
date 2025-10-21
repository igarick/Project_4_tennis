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
import java.util.List;

@WebServlet("/matches")
public class Matches extends HttpServlet {
    private static final String MATCHES_JSP = "matches";

    private static final String NAME_ERROR = "The name must be 1 - 15 English letters";

    private static final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
    private static final PaginationService paginationService = new PaginationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String paramFilter = req.getParameter("filter_by_player_name");
        List<MatchDto> matches = List.of();


        if (paramFilter == null) {      // если
            String paramPage = req.getParameter("page");

            int currentPage;
            if (paramPage == null) {
                currentPage = 1;
            } else {
                Long page = Long.parseLong(paramPage);
                currentPage = Math.toIntExact(page);
            }

//            if (page == null) {
//                currentPage = 1;
//            } else {
//                currentPage = Math.toIntExact(page);
//            }

            int pageSize = 3;

            int offset = (pageSize * (currentPage - 1) + 1);
            int limit = (pageSize);

            matches = finishedMatchesPersistenceService.findAll(offset, limit);
            Long amountId = paginationService.countId();

            System.out.println(("****************************"));
            int size = matches.size();
            System.out.println("всего лист матчей" + size);

            System.out.println("всего ид матчей" + amountId);
            System.out.println(("****************************"));

            int totalPages = (int) Math.ceil((double) amountId / pageSize);

            req.setAttribute("totalPages", totalPages);
            req.setAttribute("matches", matches);
            req.setAttribute("currentPage", currentPage);

        } else if (!RequestValidator.isValidFormat(paramFilter)) {
            req.setAttribute("error", NAME_ERROR);
        } else {
            PlayerNameDto nameDto = new PlayerNameDto(paramFilter);
            matches = finishedMatchesPersistenceService.findByName(nameDto);
        }


//        int pageSize = 3;
//        Long countPages = (long) matches.size();
//        int lastPage = (int) Math.ceil((double) countPages / pageSize);

        req.setAttribute("matches", matches);
        req.getRequestDispatcher(JspHelper.getPath(MATCHES_JSP)).forward(req, resp);
    }
}
