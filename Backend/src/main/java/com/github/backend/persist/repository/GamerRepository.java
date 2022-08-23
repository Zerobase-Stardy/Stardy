package com.github.backend.persist.repository;

import com.github.backend.persist.entity.Gamer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GamerRepository extends JpaRepository<Gamer, Long> {
    Optional<Gamer> findByNickName(String nickName);
    boolean existsByNickName(String nickName);



}
