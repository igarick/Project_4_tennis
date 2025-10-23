package com.tennis.service;

import com.tennis.dao.MatchDao;
import com.tennis.dto.MatchesPaginationDto;
import com.tennis.dto.MatchDto;
import com.tennis.dto.PageDto;
import com.tennis.dto.PlayerNameDto;

import java.util.List;

public class MatchService {
    private static final int LIMIT = 3;

    MatchDao matchDao = new MatchDao();

    private static final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
    private static final PaginationService paginationService = new PaginationService();


    public MatchesPaginationDto determineParamPaginationForMatches(PageDto currentPageDto, PlayerNameDto nameDto) {

//        long l = Long.parseLong(paramPage);
//        int currentPage = Math.toIntExact(l);
        int currentPage = currentPageDto.page();
        String name = nameDto.name();

        long amount = 0;
        List<MatchDto> matches = List.of();

        int offset = (LIMIT * (currentPage - 1));

        if (name == null || name.isBlank()) {
            matches = finishedMatchesPersistenceService.findAll(offset, LIMIT);
            amount = matchDao.countMatches(); // paginationService.countMatches();
        } else {
//            PlayerNameDto nameDto = new PlayerNameDto(paramFilter);

            matches = finishedMatchesPersistenceService.findByName(nameDto, offset, LIMIT);
            amount = matchDao.countPlayersByName(name); //paginationService.countPlayersByName(nameDto);

            req.setAttribute("paramFilter", paramFilter);
        }

        int totalPages = (int) Math.ceil((double) amount / LIMIT);



        MatchesPaginationDto dto = new MatchesPaginationDto(
                totalPages,
                currentPage,
                amount,
                matches
        );

        return dto;
    }
}
