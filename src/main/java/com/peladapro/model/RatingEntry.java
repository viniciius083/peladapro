package com.peladapro.model;

import jakarta.persistence.Entity;
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

    private String appraiser;
    private Integer rating;
    private LocalDate ratedAt;
}
