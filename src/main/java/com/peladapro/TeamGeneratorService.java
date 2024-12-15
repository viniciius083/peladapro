package com.peladapro;

import com.peladapro.model.Player;

import java.util.*;

public class TeamGeneratorService {
    private List<Player> players;

    public TeamGeneratorService(List<Player> players) {
        this.players = players;
    }

    // Método para encontrar times
    public Map<Integer, List<Player>> findTeams(List<Integer> guess, int numTeams) {
        Map<Integer, List<Player>> teams = new HashMap<>();
        for (int i = 1; i <= numTeams; i++) {
            teams.put(i, new ArrayList<>());
        }

        for (int i = 0; i < guess.size(); i++) {
            teams.get(guess.get(i)).add(players.get(i));
        }

        return teams;
    }

    public double calculateMaxDiff(Map<Integer, List<Player>> teams) {
        List<Double> averages = teams.values().stream()
            .map(team -> team.stream().mapToDouble(Player::getRating).average().orElse(0.0))
            .toList();
        return Collections.max(averages) - Collections.min(averages);
    }

    // Método para gerar times otimizados
    public List<Integer> generateOptimizedTeams(List<Integer> initialGuess, int numTeams) {
        List<Integer> bestGuess = new ArrayList<>(initialGuess);
        double bestMaxDiff = Double.MAX_VALUE;
        Random random = new Random();

        int maxTrials = 10000;
        for (int trial = 0; trial < maxTrials; trial++) {
            // Cria uma cópia para evitar alterar initialGuess diretamente
            List<Integer> currentGuess = new ArrayList<>(initialGuess);
            Collections.shuffle(currentGuess, random);
            Map<Integer, List<Player>> teams = findTeams(currentGuess, numTeams);
            double currentMaxDiff = calculateMaxDiff(teams);

            if (currentMaxDiff < bestMaxDiff) {
                bestMaxDiff = currentMaxDiff;
                bestGuess = new ArrayList<>(currentGuess);
            }
        }
        return bestGuess;
    }

    // Método principal para gerar times balanceados
    public Map<String, Object> generateTeamsWithReserve(int numTeams) {
        int totalPlayers = players.size();
        int maxPlayersInTeams = (totalPlayers / numTeams) * numTeams; // Máximo de jogadores distribuíveis
        Collections.shuffle(players);
        List<Player> reservePlayers = players.subList(maxPlayersInTeams, totalPlayers); // Jogadores extras
        List<Player> playersInTeams = players.subList(0, maxPlayersInTeams); // Jogadores alocados nos times

        // Cria uma distribuição inicial balanceada para jogadores em times
        List<Integer> initialGuess = createBalancedInitialGuess(playersInTeams.size(), numTeams);
        List<Integer> optimizedGuess = generateOptimizedTeams(initialGuess, numTeams);
        Map<Integer, List<Player>> teams = findTeams(optimizedGuess, numTeams);

        // Retorna os times e a lista de reservas
        Map<String, Object> result = new HashMap<>();
        result.put("teams", teams);
        result.put("reserve", reservePlayers);

        return result;
    }

    // Cria uma distribuição inicial balanceada
    private List<Integer> createBalancedInitialGuess(int totalPlayers, int numTeams) {
        List<Integer> guess = new ArrayList<>();
        for (int i = 0; i < totalPlayers; i++) {
            guess.add(i % numTeams + 1);
        }
        return guess;
    }
//
//    // Filtra jogadores confirmados
//    public static List<Player> processPlayers(List<Player> players) {
//        return players.stream()
//            .filter(Player::isGoing)
//            .sorted(Comparator.comparingInt(Player::getRating).reversed())
//            .collect(Collectors.toList());
//    }
}
