package com.peladapro.repository;

import com.peladapro.model.UserCommon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserCommon, Long> {
    Optional<UserCommon> findByUsername(String username);
}
