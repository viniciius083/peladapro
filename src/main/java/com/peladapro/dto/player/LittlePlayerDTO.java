package com.peladapro.dto.player;

public record LittlePlayerDTO(String name, double rating){

    public LittlePlayerDTO(PlayerDTO p) {
        this(p.getName(), p.getRating());
    }
}
