package com.tennis.service;

import com.tennis.model.MatchScoreModel;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ToString
public class OngoingMatchesService {
    private static final Map<UUID, MatchScoreModel> currentMatches = new HashMap<>();

    public MatchScoreModel getCurrentMatch(UUID uuid) {
        return currentMatches.get(uuid);
    }

    public void writeCurrentMatch(UUID uuid, MatchScoreModel matchScoreModel) {
        currentMatches.put(uuid, matchScoreModel);
    }

    public void removeCurrentMatch(UUID uuid) {
        currentMatches.remove(uuid);
    }
}
