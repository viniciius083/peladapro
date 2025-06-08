package com.peladapro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_ratings")
@Getter
@Setter
public class RatingEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String appraiser;
    private Integer rating;
    private LocalDate ratedAt;

    public RatingEntry(String player, int rating, LocalDate now) {
        this.appraiser = player;
        this.rating = rating;
        this.ratedAt = now;
    }
}
