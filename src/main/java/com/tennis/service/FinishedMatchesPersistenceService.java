package com.tennis.service;

import com.tennis.dao.MatchDao;
import com.tennis.dto.MatchDto;
import com.tennis.dto.PlayerNameDto;
import com.tennis.entity.Match;

import java.util.List;

public class FinishedMatchesPersistenceService {
    private static final MatchDao matchDao = new MatchDao();

    public void save(Match match) {
        matchDao.save(match);
    }

    public List<MatchDto> findAll(int offset, int limit) {
        return matchDao.findAll(offset, limit)
                .stream()
                .map(this::buildMatchDto)
                .toList();
    }

    public List<MatchDto> findByName(PlayerNameDto dto, int offset, int limit) {
        String name = dto.name();

        return matchDao.findByName(name, offset, limit)
                .stream()
                .map(this::buildMatchDto)
                .toList();
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
