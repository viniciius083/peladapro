package com.peladapro.dto.player;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.peladapro.model.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO{

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;
    private double rating;

    private Boolean going;

    public PlayerDTO() {
        this.setId(null);
        this.setName(null);
        this.setRating(0);
        this.setGoing(false);
    }

    public PlayerDTO(Player player) {
        this.setId(player.getId());
        this.setName(player.getName());
        this.setRating(player.getRating());
        this.setGoing(player.isGoing());

    }
}
