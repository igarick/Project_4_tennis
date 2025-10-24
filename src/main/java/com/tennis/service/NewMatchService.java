package com.tennis.service;

import com.tennis.dao.PlayerDao;
import com.tennis.dto.PlayerDto;
import com.tennis.entity.Player;

import java.util.List;

public class NewMatchService {
    private static final PlayerDao playerDao = new PlayerDao();

    public Player get(PlayerDto dto) {
        List<Player> players = playerDao.findByName(dto.name());
        if (players.isEmpty()) {
            return playerDao.save(dto.name());
        }
        return players.getFirst();
    }
}
