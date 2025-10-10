package com.tennis.service;

import com.tennis.dao.NewMatchDao;
import com.tennis.dto.PlayerDto;
import com.tennis.model.Player;

import java.util.List;
import java.util.Optional;

public class NewMatchService {
    private static final NewMatchDao newMatchDao = new NewMatchDao();

    public Player get(PlayerDto dto) {
        List<Player> players = newMatchDao.findByName(dto.name());
        if (players.isEmpty()) {
            return newMatchDao.save(dto.name());
        }
        return players.getFirst();
    }
//
//    public Player save(PlayerDto dto) {
//        return newMatchDao.save(dto.name());
//    }
}
