package com.github.backend.persist.course;


import com.github.backend.persist.common.BaseTimeEntity;
import com.github.backend.persist.gamer.Gamer;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Course extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String videoUrl;

    @Column(nullable = false)
    private String thumbnailUrl;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String level;

    @Column(nullable = false)
    private String race;

    @Column(nullable = false)
    private Long price;

    @ManyToOne
    private Gamer gamer;


}
