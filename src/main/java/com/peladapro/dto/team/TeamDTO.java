package com.peladapro.dto.team;

import com.peladapro.dto.player.LittlePlayerDTO;
import com.peladapro.model.Player;

import java.util.List;
import java.util.Map;

public record TeamDTO(String name,double rating, List<LittlePlayerDTO> players) {
    public TeamDTO(Map.Entry<Integer, List<Player>> e) {
        this(e.getKey().toString(), e.getValue().stream().map(Player::getRating).mapToDouble(Double::valueOf).average().orElse(0),e.getValue().stream().map(LittlePlayerDTO::new).toList());
    }
}
