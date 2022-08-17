package com.github.backend.persist.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Gamer {
    /**
     * id : idx(pk)
     * name : gamer Name
     * race : gamer race
     * nickName : gamer nickName
     * introduce : gamer introduce
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String race;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column
    private String introduce;
}
