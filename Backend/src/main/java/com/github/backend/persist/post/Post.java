package com.github.backend.persist.post;

import com.github.backend.persist.common.BaseTimeEntity;
import com.github.backend.persist.member.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String boardKind;

    @Column(nullable = true)
    private String imagePath;

    @ManyToOne
    private Member member;
}
