package com.tennis.dto;

import java.util.List;

public record MatchesPaginationDto(int totalPages, int currentPage, Long amountMatches, List<MatchDto> matchesDto) {
}
