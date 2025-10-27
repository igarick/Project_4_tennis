package com.tennis.service;

import com.tennis.dao.MatchDao;
import com.tennis.dto.MatchDto;
import com.tennis.dto.PaginatedMatchesDto;
import com.tennis.dto.RequestMatchParamsDto;

import java.util.List;

public class MatchService {
    private static final int LIMIT = 3;

    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService; // = new FinishedMatchesPersistenceService();
    private final MatchDao matchDao; // = new MatchDao();

    public MatchService(FinishedMatchesPersistenceService finishedMatchesPersistenceService,
                        MatchDao matchDao) {
        this.finishedMatchesPersistenceService = finishedMatchesPersistenceService;
        this.matchDao = matchDao;
    }

    public PaginatedMatchesDto getPaginatedMatches(RequestMatchParamsDto paramsDto) {
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

        return new PaginatedMatchesDto(
                totalPages,
                currentPage,
                amountMatches,
                matches
        );
    }
}
