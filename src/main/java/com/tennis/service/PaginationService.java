package com.tennis.service;

import com.tennis.dao.MatchDao;
import com.tennis.dto.PlayerNameDto;

public class PaginationService {
    MatchDao matchDao = new MatchDao();

    public Long countId() {
        return matchDao.countId();
    }

    public Long countIdByName(PlayerNameDto dto) {
        return matchDao.countIdByName(dto.name());
    }

}
