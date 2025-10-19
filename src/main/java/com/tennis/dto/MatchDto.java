package com.tennis.dto;

import com.tennis.entity.Player;

public record MatchDto(Integer ID, Player player1, Player player2, Player winner) {
}
