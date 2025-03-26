package com.peladapro.service;

import com.peladapro.dto.team.TeamShuffledDTO;
import com.peladapro.model.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class TeamService {


    private final PlayerService playerService;

    /**
     * Método para iniciar o processo de geração de times
     * @param numberOfTeams número de times
     * @return Times balanceados de acordo com as avaliações do jogador
     */
    public TeamShuffledDTO balanceTeams(int numberOfTeams) {
        return generateTeamsWithReserve(numberOfTeams);
    }

    /**
     * Método para preencher uma distribuição inicial com jogadores
     * @param guess distribuição inicial
     * @param numTeams numeros de times
     * @param players jogadores
     * @return retorna um time preenchido com jogadores/rating
     */
    private Map<Integer, List<Player>> findTeams(List<Integer> guess, int numTeams, List<Player> players) {
        Map<Integer, List<Player>> teams = new HashMap<>();
        for (int i = 1; i <= numTeams; i++) {
            teams.put(i, new ArrayList<>());
        }

        for (int i = 0; i < guess.size(); i++) {
            teams.get(guess.get(i)).add(players.get(i));
        }

        return teams;
    }

    /**
     * Método que calcula o valor maximo da diferença entre os times
     * @param players lista de jogadaores
     * @return retorna o valor maximo da diferença entre os times
     */
    private double calculateMaxDiff(Map<Integer, List<Player>> players) {
        List<Double> averages = players.values().stream()
                .map(team -> team.stream().mapToDouble(Player::getRating).average().orElse(0.0))
                .toList();
        return Collections.max(averages) - Collections.min(averages);
    }

    /**
     * Método que gera times, tentando várias vezes até achar a menor diferência média entre os times
     * @param initialGuess distribuição inicial
     * @param numTeams numeros de times
     * @param players jogadores
     * @return retorna o time mais balanceado possível entre as tentativas de balanceamento
     */
    private List<Integer> generateOptimizedTeams(List<Integer> initialGuess, int numTeams, List<Player> players) {
        List<Integer> bestGuess = new ArrayList<>(initialGuess); //palpite inicial
        double bestMaxDiff = Double.MAX_VALUE;
        Random random = new Random();

        int maxTrials = 10000;
        for (int trial = 0; trial < maxTrials; trial++) {
            // Cria uma cópia para evitar alterar initialGuess diretamente
            List<Integer> currentGuess = new ArrayList<>(initialGuess);
            // Embaralha os jogadores
            Collections.shuffle(currentGuess, random);
            // Cria um novo time
            Map<Integer, List<Player>> teams = findTeams(currentGuess, numTeams, players);
            // Calcula a diferença entre os times
            double currentMaxDiff = calculateMaxDiff(teams);

            // Atualiza o melhor palpite
            if (currentMaxDiff < bestMaxDiff) {
                bestMaxDiff = currentMaxDiff;
                bestGuess = new ArrayList<>(currentGuess);
            }
        }
        return bestGuess;
    }

    /**
     * Método principal para gerar times balanceados
     * @param numTeams número de times
     * @return Times balanceados de acordo com as avaliações do jogador
     */
    private TeamShuffledDTO generateTeamsWithReserve(int numTeams) {
        List<Player> players = playerService.getConfirmedPlayers();
        int totalPlayers = players.size();
        int maxPlayersInTeams = (totalPlayers / numTeams) * numTeams; // Máximo de jogadores distribuíveis
        Collections.shuffle(players); // misturar para selecionar os reservas
        List<Player> reservePlayers = players.subList(maxPlayersInTeams, totalPlayers); // Jogadores extras
        List<Player> playersInTeams = players.subList(0, maxPlayersInTeams); // Jogadores alocados nos times
        // Cria uma distribuição inicial balanceada para jogadores em times
        List<Integer> initialGuess = createBalancedInitialGuess(playersInTeams.size(), numTeams);
        Collections.shuffle(initialGuess);
        List<Integer> optimizedGuess = generateOptimizedTeams(initialGuess, numTeams, playersInTeams);
        Map<Integer, List<Player>> teams = findTeams(optimizedGuess, numTeams, playersInTeams);

        return new TeamShuffledDTO(teams, reservePlayers);
    }

    /**
     * Cria uma lista inicial balanceada
     * @param totalPlayers quantidade de jogadores
     * @param numTeams numeros de times
     * @return uma distribuição inicial balanceada
     */
    private List<Integer> createBalancedInitialGuess(int totalPlayers, int numTeams) {
        List<Integer> guess = new ArrayList<>();
        for (int i = 0; i < totalPlayers; i++) {
            guess.add(i % numTeams + 1);
        }
        Collections.shuffle(guess);
        return guess;
    }

    // Filtra jogadores confirmados
    private static List<Player> processPlayers(List<Player> players) {
        return players.stream()
            .filter(Player::isGoing)
            .sorted(Comparator.comparingDouble(Player::getRating).reversed())
            .toList();
    }
}
