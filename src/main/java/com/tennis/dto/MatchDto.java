package com.tennis.dto;

import com.tennis.model.Player;

public record MatchDto (Integer ID, Player PLAYER1, Player PLAYER2, Player WINNER) {
}
