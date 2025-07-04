package com.peladapro.model;

import com.peladapro.dto.player.PlayerDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private boolean going;

    @ElementCollection
    @CollectionTable(name = "ratings", joinColumns = @JoinColumn(name = "player_id"))
    private List<RatingEntry> ratings = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserCommon user;

    public Player(PlayerDTO playerDTO) {
        this.name = playerDTO.getName();
        this.rating = playerDTO.getRating();
        this.going = playerDTO.getGoing();
    }

    public List<RatingEntry> getRatingsInternal() {
        return ratings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id != null && id.equals(player.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
