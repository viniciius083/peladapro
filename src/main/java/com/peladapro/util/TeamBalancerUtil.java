package com.peladapro.util;

import com.peladapro.dto.player.PlayerDTO;

import java.util.*;

public class TeamBalancerUtil {

    /**
     * Gera uma distribuição otimizada de jogadores nos times, buscando minimizar a diferença entre as médias dos times.
     *
     * @param initialGuess distribuição inicial dos jogadores
     * @param numTeams número de times
     * @param players lista de jogadores a serem distribuídos
     * @return melhor distribuição encontrada dos jogadores nos times
     */
    public static List<Integer> generateOptimizedTeams(List<Integer> initialGuess, int numTeams, List<PlayerDTO> players, int maxTrials) {
        List<Integer> bestGuess = new ArrayList<>(initialGuess); //palpite inicial
        double bestMaxDiff = Double.MAX_VALUE;
        Random random = new Random();

        for (int trial = 0; trial < maxTrials; trial++) {
            // Cria uma cópia para evitar alterar initialGuess diretamente
            List<Integer> currentGuess = new ArrayList<>(initialGuess);
            // Embaralha os jogadores
            Collections.shuffle(currentGuess, random);
            // Cria um novo time
            Map<Integer, List<PlayerDTO>> teams = findTeams(currentGuess, numTeams, players);
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
     * Preenche uma distribuição de jogadores em times conforme o palpite fornecido.
     *
     * @param guess distribuição dos jogadores nos times (índice representa o jogador, valor representa o time)
     * @param numTeams número de times
     * @param players lista de jogadores a serem distribuídos
     * @return mapa com o número do time como chave e a lista de jogadores como valor
     */
    public static Map<Integer, List<PlayerDTO>> findTeams(List<Integer> guess, int numTeams, List<PlayerDTO> players) {
        Map<Integer, List<PlayerDTO>> teams = new HashMap<>();
        for (int i = 1; i <= numTeams; i++) {
            teams.put(i, new ArrayList<>());
        }

        for (int i = 0; i < guess.size(); i++) {
            teams.get(guess.get(i)).add(players.get(i));
        }

        return teams;
    }

    /**
     * Calcula a diferença máxima entre as médias de rating dos times.
     *
     * @param players mapa de times e seus jogadores
     * @return diferença máxima entre as médias dos times
     */
    public static double calculateMaxDiff(Map<Integer, List<PlayerDTO>> players) {
        List<Double> averages = players.values().stream()
                .map(team -> team.stream().mapToDouble(PlayerDTO::getRating).average().orElse(0.0))
                .toList();
        return Collections.max(averages) - Collections.min(averages);
    }


    /**
     * Cria uma lista inicial balanceada
     *
     * @param totalPlayers quantidade de jogadores
     * @param numTeams numeros de times
     * @return uma distribuição inicial balanceada
     */
    public static List<Integer> createBalancedInitialGuess(int totalPlayers, int numTeams) {
        List<Integer> guess = new ArrayList<>();
        for (int i = 0; i < totalPlayers; i++) {
            guess.add(i % numTeams + 1);
        }

        Collections.shuffle(guess);
        return guess;
    }
}
