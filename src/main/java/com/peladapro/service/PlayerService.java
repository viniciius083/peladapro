package com.peladapro.service;

import com.peladapro.dto.player.PlayerDTO;
import com.peladapro.dto.vote.VoteDTO;
import com.peladapro.exception.ResourceNotFoundException;
import com.peladapro.model.Player;
import com.peladapro.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    // Criar um novo jogador
    public Player createPlayer(PlayerDTO playerDTO) {
        Player player = new Player(playerDTO);
        return playerRepository.save(player);
    }

    // Listar todos os jogadores
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Page<PlayerDTO> getAllPlayersByRating(int size, int page) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "rating"));
        Page<Player> players = playerRepository.findAll(pageRequest);

        return players.map(PlayerDTO::new);
    }

    // Buscar jogador por ID
    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found with id: " + id));
    }

    // Avaliar um jogador
    public Player evaluatePlayer(Long evaluatorId, Long id, int score) {
        Player player = getPlayerById(id);
        Player playerEvaluator = getPlayerById(evaluatorId);
        player.evaluate(playerEvaluator.getName(), score);
        return playerRepository.save(player);
    }


    public void updatePresence(List<PlayerDTO> players) {
        for (PlayerDTO player : players) {
            Player entity = playerRepository.findById(player.getId())
                    .orElseThrow(() -> new RuntimeException("Player not found"));
            entity.setGoing(player.getGoing() != null ? player.getGoing() : entity.isGoing());
            playerRepository.save(entity);
        }
    }


    // Filtra jogadores confirmados
    public List<Player> getConfirmedPlayers() {
        return getAllPlayers().stream()
            .filter(Player::isGoing)
            .collect(Collectors.toList());
    }

    public void submitVotes(String username, List<VoteDTO> votes) {
        for(VoteDTO vote : votes) {
            if(vote.getVote() > 0){
                Player player = getPlayerById(vote.getId());
                player.evaluate(username, vote.getVote());
                playerRepository.save(player);
            }
        }
    }
}
