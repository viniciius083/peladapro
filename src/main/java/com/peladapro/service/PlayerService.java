package com.peladapro.service;

import com.peladapro.dto.player.PlayerDTO;
import com.peladapro.dto.vote.VoteDTO;
import com.peladapro.exception.ResourceNotFoundException;
import com.peladapro.model.Player;
import com.peladapro.model.RatingEntry;
import com.peladapro.model.UserCommon;
import com.peladapro.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public void updatePresence(List<PlayerDTO> players) {
        List<Player> entities = players.stream()
                .map(player -> {
                    Player entity = playerRepository.findById(player.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
                    entity.setGoing(player.getGoing() != null ? player.getGoing() : entity.isGoing());
                    return entity;
                })
                .collect(Collectors.toList());
        playerRepository.saveAll(entities);
    }


    // Filtra jogadores confirmados
    public List<PlayerDTO> getConfirmedPlayers() {
        return getAllPlayers().stream()
                .filter(Player::isGoing)
                .map(PlayerDTO::new)
                .collect(Collectors.toList());
    }

    public void submitVotes(String username, List<VoteDTO> votes) {
        for(VoteDTO vote : votes) {
            if(vote.getVote() > 0){
                Player player = getPlayerById(vote.getId());
                evaluate(player, username, vote.getVote());
                playerRepository.save(player);
            }
        }
    }

    public void evaluate(Player player, String appraiser, int rating) {
        boolean votedRecently = player.getRatings().stream()
                .filter(entry -> entry.getAppraiser().equals(appraiser))
                .anyMatch(entry -> entry.getRatedAt() != null
                        && entry.getRatedAt().isAfter(LocalDate.now().minusDays(6)));

        if (!votedRecently) {
            player.getRatingsInternal().add(new RatingEntry(appraiser, rating, LocalDate.now()));
            calculateAverageSkill(player);
        }
    }

    private void calculateAverageSkill(Player player) {
        if (!player.getRatings().isEmpty()) {
            double sum = player.getRatings().stream()
                    .mapToInt(RatingEntry::getRating)
                    .sum();
            player.setRating(sum / player.getRatings().size());
        }
    }

    public void createPlayerByUser(UserCommon user) {
        playerRepository.save(new Player(user));
    }
}
