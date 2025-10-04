package com.tennis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchScore {
    private int FirstPlayerId;
    private int SecondPlayerId;
    private List<SetScore> score = new ArrayList<>();
}
