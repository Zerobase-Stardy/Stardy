package com.github.backend.persist.attendance;


import com.github.backend.persist.common.BaseTimeEntity;
import com.github.backend.persist.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {
	@UniqueConstraint(name = "attendanceCheck", columnNames = {"member_id", "attendanceDate"})
}
)
@Entity
public class Attendance extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn
	private Member member;

	private LocalDate attendanceDate;


}
