package com.tennis.service;

import com.tennis.dao.MatchDao;
import com.tennis.dto.PlayerNameDto;

public class PaginationService {
    MatchDao matchDao = new MatchDao();

    public Long countMatches() {
        return matchDao.countMatches();
    }

    public Long countPlayersByName(PlayerNameDto dto) {
        return matchDao.countMatchesByPlayerName(dto.name());
    }

}
