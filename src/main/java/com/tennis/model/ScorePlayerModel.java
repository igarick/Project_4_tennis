package com.tennis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScorePlayerModel {
    private Integer playerId;
    private Integer setScore;
    private Integer gameScore;
    private Integer pointScore;
}
