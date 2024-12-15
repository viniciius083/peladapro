package com.peladapro.repository;

import com.peladapro.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
