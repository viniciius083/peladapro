package com.peladapro.dto.team;

import com.peladapro.dto.player.LittlePlayerDTO;
import com.peladapro.model.Player;

import java.util.List;
import java.util.Map;

public record TeamShuffledDTO(List<TeamDTO> teams,List<LittlePlayerDTO> reservePlayers) {
    public TeamShuffledDTO(Map<Integer, List<Player>> teams, List<Player> reservePlayers) {
        this(teams.entrySet().stream().map(TeamDTO::new).toList(), reservePlayers.stream().map(LittlePlayerDTO::new).toList());
    }
}
