package com.peladapro.controller;

import com.peladapro.dto.team.TeamShuffledDTO;
import com.peladapro.model.Player;
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
        List<Player> players = playerService.getConfirmedPlayers();
        model.addAttribute("players", players);
        return "shuffle";
    }

    @PostMapping("/sort")
    public String sortTeams(@RequestParam int numTeams, Model model) {
        TeamShuffledDTO sortedData = teamService.balanceTeams(numTeams);
        model.addAttribute("teams", sortedData.teams());
        model.addAttribute("reservePlayers", sortedData.reservePlayers());
        return "shuffleTeam";
    }

}
