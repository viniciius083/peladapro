package com.peladapro.controller;

import com.peladapro.dto.player.PlayerDTO;
import com.peladapro.dto.team.TeamShuffledDTO;
import com.peladapro.model.Player;
import com.peladapro.service.PlayerService;
import com.peladapro.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PlayerApiController {

    private final PlayerService playerService;
    private final TeamService teamService;

    public PlayerApiController(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    // Criar um novo jogador
    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody PlayerDTO playerDTO) {
        Player createdPlayer = playerService.createPlayer(playerDTO);
        return ResponseEntity.ok(createdPlayer);
    }

    // Avaliar um jogador
    @PostMapping("/players/{id}/evaluate")
    public ResponseEntity<Player> evaluatePlayer(@RequestHeader Long evaluatorId, @RequestParam int score, @RequestParam Long id) {
        Player updatedPlayer = playerService.evaluatePlayer(evaluatorId, id, score);
        return ResponseEntity.ok(updatedPlayer);
    }

    @PostMapping("/teams/shuffle")
    public ResponseEntity<TeamShuffledDTO> balanceTeams(@RequestParam int numberOfTeams) {
        TeamShuffledDTO balancedTeams = teamService.balanceTeams(numberOfTeams);
        return ResponseEntity.ok(balancedTeams);
    }


}
