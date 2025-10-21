package com.tennis.service;

import com.tennis.dao.MatchDao;

public class PaginationService {
    MatchDao matchDao = new MatchDao();

    public Long countId() {
        return matchDao.countId();
    }

}
