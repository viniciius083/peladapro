package com.peladapro.model;

import com.peladapro.dto.player.PlayerDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private List<RatingEntry> ratings = new ArrayList<>();

    public Player(PlayerDTO playerDTO) {
        this.name = playerDTO.getName();
        this.rating = playerDTO.getRating();
        this.isGoing = playerDTO.getGoing();
    }

    public void evaluate(String player, int rating) {
        boolean votedRecently = ratings.stream()
                .filter(entry -> entry.getAppraiser().equals(player))
                .anyMatch(entry -> entry.getRatedAt() != null
                        && entry.getRatedAt().isAfter(LocalDate.now().minusDays(6)));

        if (!votedRecently) {
            this.ratings.add(new RatingEntry(player, rating, LocalDate.now()));
            calculateAverageSkill();
        }
    }

    /**
     * Calcula a media das avaliacoes
     */
    private void calculateAverageSkill() {
        if (!ratings.isEmpty()) {
            double sum = ratings.stream()
                    .mapToInt(RatingEntry::getRating)
                    .sum();
            this.rating = sum / ratings.size();
        }
    }

    @Override
    public String toString() {
        return name + " (Habilidade: " + String.format("%.2f", rating) + ")";
    }
}
