package com.peladapro.dto.player;

import com.peladapro.model.Player;

public record LittlePlayerDTO(String name, double rating){

    public LittlePlayerDTO(Player p) {
        this(p.getName(), p.getRating());
    }
}
