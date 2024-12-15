package com.peladapro.wrappers;

import com.peladapro.dto.player.PlayerDTO;

import java.util.List;

public class PlayerListWrapper {
    private List<PlayerDTO> players;

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }
}
