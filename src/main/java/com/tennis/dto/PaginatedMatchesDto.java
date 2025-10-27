package com.tennis.dto;

import java.util.List;

public record PaginatedMatchesDto(int totalPages, int currentPage, Long amountMatches, List<MatchDto> matchesDto) {
}
