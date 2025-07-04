package com.peladapro.dto.player;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.peladapro.model.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO{

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;
    private double rating;

    private Boolean going;

    public PlayerDTO(Player player) {
        this.setId(player.getId());
        this.setName(player.getName());
        this.setRating(player.getRating());
        this.setGoing(player.isGoing());

    }
}
