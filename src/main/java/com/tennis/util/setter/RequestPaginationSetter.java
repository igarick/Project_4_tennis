package com.tennis.util.setter;

import com.tennis.dto.PaginatedMatchesDto;
import jakarta.servlet.http.HttpServletRequest;

public class RequestPaginationSetter {
    public static void SetAttributes(HttpServletRequest req, String paramFilter, PaginatedMatchesDto dto) {
        req.setAttribute("paramFilter", paramFilter);
        req.setAttribute("totalPages", dto.totalPages());
        req.setAttribute("currentPage", dto.currentPage());
        req.setAttribute("matches", dto.matchesDto());
    }
}
