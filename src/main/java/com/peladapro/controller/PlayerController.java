package com.peladapro.controller;

import com.peladapro.dto.player.PlayerDTO;
import com.peladapro.dto.vote.VoteDTO;
import com.peladapro.service.PlayerService;
import com.peladapro.wrappers.PlayerListWrapper;
import com.peladapro.wrappers.VoteListWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/ranking")
    public String showRanking(Model model, @PageableDefault() Pageable pageable) {
        Page<PlayerDTO> players = playerService.getAllPlayersByRating(pageable.getPageSize(), pageable.getPageNumber());
        model.addAttribute("position", pageable.getPageNumber() * 10 + 1);
        model.addAttribute("playersPage", players);
        return "ranking";
    }

    @GetMapping("/players")
    public String createPlayer(Model model) {
        model.addAttribute("player", new PlayerDTO());
        return "createPlayer";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/players/create")
    public String createPlayer(@Valid @ModelAttribute PlayerDTO player, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("player", player);
            return "createPlayer";
        }
        playerService.createPlayer(player);
        return "redirect:/players?success";
    }

    @GetMapping("/players/update")
    public String managePlayers(Model model) {
        List<PlayerDTO> players = playerService.getAllPlayers().stream().map(PlayerDTO::new).toList();
        PlayerListWrapper wrapper = new PlayerListWrapper();
        wrapper.setPlayers(players);
        model.addAttribute("playerListWrapper", wrapper);
        return "updatePresence";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/players/update")
    public String updatePlayerPresence(@ModelAttribute("playerListWrapper") PlayerListWrapper wrapper) {
        playerService.updatePresence(wrapper.getPlayers());
        return "redirect:/players/update";
    }


    @GetMapping("/vote")
    public String votePage(Model model) {
        List<PlayerDTO> players = playerService.getConfirmedPlayers();
        model.addAttribute("players", players);
        model.addAttribute("votingList", new VoteListWrapper());
        return "votePage";
    }

    @PostMapping("/voting/submit")
    public String submitVotes(HttpServletRequest ignoredRequest, @ModelAttribute VoteListWrapper votingList) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<VoteDTO> votes = votingList.getVotes();
        boolean success = playerService.submitVotes(username, votes);
        return "redirect:/vote?success="+success;
    }

}
