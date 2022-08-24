package com.github.backend.persist.gamer.repository;

import com.github.backend.persist.gamer.Gamer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamerRepository extends JpaRepository<Gamer, Long> {
    boolean existsByNickname(String nickname);
}
