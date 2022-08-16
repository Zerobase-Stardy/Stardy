package com.github.backend.persist.entity;

import com.github.backend.model.constants.Role;
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
public class Admin extends BaseTimeEntity{
    /**
     * id : Admin ID
     * password : Admin Password
     * role : Admin Role
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String adminId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

}
