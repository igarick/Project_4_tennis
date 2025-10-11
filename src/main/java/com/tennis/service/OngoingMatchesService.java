package com.tennis.service;

import com.tennis.model.GameModel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OngoingMatchesService {
    private final Map<UUID, GameModel> currentMatches = new HashMap<>();

    public GameModel getCurrentMatch(UUID uuid) {
        return currentMatches.get(uuid);
    }

    public void setMatches(UUID uuid, GameModel gameModel) {
        currentMatches.put(uuid, gameModel);
    }
}
