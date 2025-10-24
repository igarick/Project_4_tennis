package com.tennis.service;

import com.tennis.dao.PlayerDao;
import com.tennis.entity.Player;
import com.tennis.model.PlayerModel;

import java.util.List;

public class NewMatchService {
    private static final PlayerDao playerDao = new PlayerDao();

    public PlayerModel get(PlayerModel model) {
        List<Player> players = playerDao.findByName(model.getName());
        if (players.isEmpty()) {
            Player player = playerDao.save(model.getName());
            return buildModel(player);
        }
        Player player = players.getFirst();
        return buildModel(player);
    }

    private PlayerModel buildModel(Player player) {
        return new PlayerModel(
                player.getId(),
                player.getName()
        );
    }
}
