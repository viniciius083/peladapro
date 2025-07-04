package com.peladapro.controller;

import com.peladapro.dto.player.PlayerDTO;
import com.peladapro.dto.team.TeamShuffledDTO;
import com.peladapro.service.PlayerService;
import com.peladapro.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller()
@AllArgsConstructor
@RequestMapping("/teams")
public class TeamController {

    private final PlayerService playerService;

    private final TeamService teamService;


    @GetMapping("/sort")
    public String showTeamSortPage(Model model) {
        List<PlayerDTO> players = playerService.getConfirmedPlayers();
        model.addAttribute("players", players);
        return "shuffle";
    }

    @PostMapping("/sort")
    public String sortTeams(@RequestParam int numTeams, Model model) {
        if (numTeams <= 0) {
            model.addAttribute("error", "O número de times deve ser maior que zero.");
            model.addAttribute("players", playerService.getConfirmedPlayers());
            return "shuffle";
        }
        try {
            TeamShuffledDTO sortedData = teamService.balanceTeams(numTeams);
            model.addAttribute("teams", sortedData.teams());
            model.addAttribute("reservePlayers", sortedData.reservePlayers());
            return "shuffleTeam";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao balancear os times: " + e.getMessage());
            model.addAttribute("players", playerService.getConfirmedPlayers());
            return "shuffle";
        }
    }

}
