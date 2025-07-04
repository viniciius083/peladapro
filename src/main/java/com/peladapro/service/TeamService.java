package com.peladapro.service;

import com.peladapro.dto.player.PlayerDTO;
import com.peladapro.dto.team.TeamShuffledDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.peladapro.util.TeamBalancerUtil;
import java.util.*;

@Service
@AllArgsConstructor
public class TeamService {


    private final PlayerService playerService;

    /**
     * Método para iniciar o processo de geração de times
     *
     * @param numberOfTeams número de times
     * @return Times balanceados de acordo com as avaliações do jogador
     */
    public TeamShuffledDTO balanceTeams(int numberOfTeams) {
        return generateTeamsWithReserve(numberOfTeams);
    }

    /**
     * Método principal para gerar times balanceados
     *
     * @param numTeams número de times
     * @return Times balanceados de acordo com as avaliações do jogador
     */
    private TeamShuffledDTO generateTeamsWithReserve(int numTeams) {
        List<PlayerDTO> players = playerService.getConfirmedPlayers();
        if (numTeams < 2) {
            throw new IllegalArgumentException("O número de times deve ser pelo menos 2.");
        }
        if (numTeams > players.size()) {
            throw new IllegalArgumentException("O número de times não pode ser maior que o número de jogadores.");
        }
        int totalPlayers = players.size();
        int maxPlayersInTeams = (totalPlayers / numTeams) * numTeams; // Máximo de jogadores distribuíveis
        Collections.shuffle(players); // misturar para selecionar os reservas
        List<PlayerDTO> reservePlayers = players.subList(maxPlayersInTeams, totalPlayers); // Jogadores extras
        List<PlayerDTO> playersInTeams = players.subList(0, maxPlayersInTeams); // Jogadores alocados nos times
        // Cria uma distribuição inicial balanceada para jogadores em times
        List<Integer> initialGuess = TeamBalancerUtil.createBalancedInitialGuess(playersInTeams.size(), numTeams);
        Collections.shuffle(initialGuess);
        List<Integer> optimizedGuess = TeamBalancerUtil.generateOptimizedTeams(initialGuess, numTeams, playersInTeams, 1000);
        Map<Integer, List<PlayerDTO>> teams = TeamBalancerUtil.findTeams(optimizedGuess, numTeams, playersInTeams);

        return new TeamShuffledDTO(teams, reservePlayers);
    }


}
