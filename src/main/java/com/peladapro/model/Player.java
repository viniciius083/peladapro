package com.peladapro.model;

import com.peladapro.dto.player.PlayerDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_players")
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private double rating;

    private boolean isGoing;

    @ElementCollection
    @CollectionTable(name = "ratings", joinColumns = @JoinColumn(name = "player_id"))
    @MapKeyColumn(name = "appraiser")
    @Column(name = "rating")
    private Map<String, Integer> ratings = new HashMap<>();

    public Player(PlayerDTO playerDTO) {
        this.name = playerDTO.getName();
        this.rating = playerDTO.getRating();
        this.isGoing = playerDTO.getGoing();
    }

    public void evaluate(String player, int rating) {
            this.ratings.put(player, rating);
            calculateAverageSkill();

    }

    /**
     * Calcula a media das avaliacoes
     */
    private void calculateAverageSkill() {
        if (!ratings.isEmpty()) {
            double sum = ratings.values().stream().mapToInt(Integer::intValue).sum();
            this.rating = sum / ratings.size();
        }
    }

    @Override
    public String toString() {
        return name + " (Habilidade: " + String.format("%.2f", rating) + ")";
    }
}
