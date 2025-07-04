package com.peladapro.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Representa uma avaliação feita por um usuário a outro.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class RatingEntry {

    @NotNull
    private String appraiser;

    @NotNull
    private Integer rating;

    @NotNull
    private LocalDate ratedAt;
}