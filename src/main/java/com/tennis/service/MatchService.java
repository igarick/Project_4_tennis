package com.tennis.service;

import com.tennis.dao.MatchDao;
import com.tennis.dto.*;

import java.util.List;

public class MatchService {
    private static final int LIMIT = 3;

    private static final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
    private static final MatchDao matchDao = new MatchDao();

    public MatchesPaginationDto determineParamPaginationForMatches(RequestMatchParamsDto paramsDto) {
        long l = Long.parseLong(paramsDto.page());
        int currentPage = Math.toIntExact(l);
        String name = paramsDto.name();

        int offset = (LIMIT * (currentPage - 1));

        long amount = 0;
        List<MatchDto> matches = List.of();

        if (name == null || name.isBlank()) {
            matches = finishedMatchesPersistenceService.findAll(offset, LIMIT);
            amount = matchDao.countMatches();
        } else {
            matches = finishedMatchesPersistenceService.findByName(name, offset, LIMIT);
            amount = matchDao.countPlayersByName(name);
        }
        int totalPages = (int) Math.ceil((double) amount / LIMIT);

        return new MatchesPaginationDto(
                totalPages,
                currentPage,
                amount,
                matches
        );
    }
}
