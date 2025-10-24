package com.tennis.service;

import com.tennis.dao.MatchDao;
import com.tennis.dto.*;

import java.util.List;

public class MatchService {
    private static final int LIMIT = 3;

    private static final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
    private static final MatchDao matchDao = new MatchDao();

    public MatchesPaginationDto getPaginatedMatches(RequestMatchParamsDto paramsDto) {
        long parsedPage = Long.parseLong(paramsDto.page());
        int currentPage = Math.toIntExact(parsedPage);
        String name = paramsDto.name();

        int offset = (LIMIT * (currentPage - 1));

        long amountMatches;
        List<MatchDto> matches;

        if (name == null || name.isBlank()) {
            matches = finishedMatchesPersistenceService.findAll(offset, LIMIT);
            amountMatches = matchDao.countMatches();
        } else {
            matches = finishedMatchesPersistenceService.findByName(name, offset, LIMIT);
            amountMatches = matchDao.countMatchesByPlayerName(name);
        }
        int totalPages = (int) Math.ceil((double) amountMatches / LIMIT);

        return new MatchesPaginationDto(
                totalPages,
                currentPage,
                amountMatches,
                matches
        );
    }
}
