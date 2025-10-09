package com.tennis.service;

import com.tennis.dao.NewMatchDao;
import com.tennis.dto.PlayerNameDto;
import com.tennis.dto.PlayersNamesDto;
import com.tennis.model.Player;

import java.util.Optional;

public class NewMatchService {
    private static final NewMatchDao newMatchDao = new NewMatchDao();

    public Player save(PlayerNameDto dto) {
       Player player = null;
        Optional<Player> playerOptional = newMatchDao.findByName(dto.name());
        if (playerOptional.isEmpty()) {
            player = newMatchDao.save(dto.name());
        }
        return player;
    }
}
