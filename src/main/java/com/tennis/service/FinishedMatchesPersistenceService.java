package com.tennis.service;

import com.tennis.dao.MatchDao;
import com.tennis.dto.MatchDto;
import com.tennis.model.Match;

public class FinishedMatchesPersistenceService {
    private static final MatchDao matchDao = new MatchDao();

    public void save(Match match) {
        matchDao.save(match);
    }
}
