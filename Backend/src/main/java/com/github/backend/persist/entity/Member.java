package com.github.backend.persist.entity;

import com.github.backend.model.constants.AuthType;
import com.github.backend.model.constants.MemberStatus;
import com.github.backend.model.constants.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Member extends BaseTimeEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String email;

	private String nickname;

	@Enumerated(EnumType.STRING)
	private MemberStatus status;

	private long point;

	@Enumerated(EnumType.STRING)
	private AuthType authType;

	@Enumerated(EnumType.STRING)
	private Role role;


	public void increasePoint(long amount) {
		this.point += amount;
	}

	public void decreasePoint(long amount) {
		this.point -= amount;
	}
}
