package com.tennis.util;

import com.tennis.entity.Match;
import com.tennis.entity.Player;
import com.tennis.model.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EntitiesMapperAndBuilder {

    public static Player mapToEntity(PlayerModel playerModel) {
        return Player.builder()
                .name(playerModel.getName())
                .id(playerModel.getId())
                .build();
    }
}
