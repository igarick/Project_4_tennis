package com.tennis.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum PointScoreEnum {
    LOVE(0),
    FIFTEEN(15),
    THIRTY(30),
    FORTY(40),
    ADVANTAGE(-1),
    WIN(-100);

    private final int value;

    PointScoreEnum(int value) {
        this.value = value;
    }

    public String displayPoint() {
        return this == ADVANTAGE ? "AD" : String.valueOf(value);
    }

}
