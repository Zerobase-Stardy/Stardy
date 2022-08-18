package com.github.backend.persist.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Gamer extends BaseTimeEntity{
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
