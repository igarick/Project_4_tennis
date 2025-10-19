package com.tennis.service;

import com.tennis.dao.MatchDao;
import com.tennis.dto.MatchDto;
import com.tennis.entity.Match;

import java.util.List;

public class FinishedMatchesPersistenceService {
    private static final MatchDao matchDao = new MatchDao();

    public void save(Match match) {
        matchDao.save(match);
    }

    public List<MatchDto> findAll() {
        return matchDao.findAll()
                .stream()
                .map(this::buildMatchDto)
                .toList();

//        return matchDao.findAll();
    }

    private MatchDto buildMatchDto(Match match) {
        return new MatchDto(
                match.getID(),
                match.getPlayer1(),
                match.getPlayer2(),
                match.getWinner()
        );
    }
}
