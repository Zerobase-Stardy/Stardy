package com.github.backend.service.admin.impl;

import com.github.backend.dto.admin.LogoutAdminOutputDto;
import com.github.backend.dto.admin.RegisterAdminOutputDto;
import com.github.backend.dto.common.AdminInfo;
import com.github.backend.dto.course.SearchCourse;
import com.github.backend.dto.member.MemberSearchOutputDto;
import com.github.backend.dto.member.SearchMember;
import com.github.backend.persist.member.repository.MemberSearchRepository;
import com.github.backend.security.jwt.Tokens;
import com.github.backend.dto.course.CourseInfoOutputDto;
import com.github.backend.dto.course.RegisterCourse;
import com.github.backend.dto.gamer.*;
import com.github.backend.exception.admin.AdminException;
import com.github.backend.exception.admin.code.AdminErrorCode;
import com.github.backend.exception.course.CourseException;
import com.github.backend.exception.course.code.CourseErrorCode;
import com.github.backend.exception.gamer.GamerErrorCode;
import com.github.backend.exception.gamer.GamerException;
import com.github.backend.persist.admin.Admin;
import com.github.backend.persist.admin.repository.AdminRepository;
import com.github.backend.persist.common.repository.RefreshTokenRepository;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.course.repository.CourseRepository;
import com.github.backend.persist.course.repository.querydsl.CourseSearchRepository;
import com.github.backend.persist.gamer.Gamer;
import com.github.backend.persist.gamer.repository.GamerRepository;
import com.github.backend.persist.gamer.repository.querydsl.GamerSearchRepository;
import com.github.backend.persist.member.type.Role;
import com.github.backend.dto.course.UpdateCourse;

import com.github.backend.security.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final GamerRepository gamerRepository;
    private final CourseRepository courseRepository;
    private final GamerSearchRepository gamerSearchRepository;
    private final CourseSearchRepository courseSearchRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberSearchRepository memberSearchRepository;

    private final TokenService tokenService;


    /**
     * Admin 등록
     * @param adminId : adminId
     * @param password : adminPassword
     * 중복된 Admin Id 등록할 수 없음.
     */
    @Transactional
    public RegisterAdminOutputDto.Info registerAdmin(String adminId, String password){

        validateCreateAdmin(adminId);

        Admin admin = adminRepository.save(Admin.builder()
                .adminId(adminId)
                .password(password)
                .role(Role.ROLE_ADMIN)
                .build());

        return RegisterAdminOutputDto.Info.of(admin);
    }

    private void validateCreateAdmin(String adminId) {
        if(adminRepository.existsByAdminId(adminId))
            throw new AdminException(AdminErrorCode.EXIST_SAME_ADMIN_ID);
    }

    /**
     * Gamer 등록
     * @param request
     * - name : 게이머 이름
     * - race : 종족
     * - nickname : 게이머 인게임 별명
     * - introduce : 자기소개
     * 중복된 nickname Gamer 등록할 수 없음.
     */

    @Transactional
    public RegisterGamerOutputDto.Info registerGamer(
            RegisterGamer.Request request
    ){

        validateCreateGamer(request.getNickname());

        Gamer gamer = gamerRepository.save(
                Gamer.builder()
                        .name(request.getName())
                        .race(request.getRace())
                        .nickname(request.getNickname())
                        .introduce(request.getIntroduce())
                        .build()
        );

        return RegisterGamerOutputDto.Info.of(gamer);

    }

    private void validateCreateGamer(String nickname) {
        if (gamerRepository.existsByNickname(nickname)) {
            throw new GamerException(GamerErrorCode.EXIST_SAME_GAMER_NICKNAME);
        }
    }

    /**
     *
     * @param gamerId : 게이머 id
     * @return 게이머 상세 정보
     */
    @Transactional
    public GamerInfoOutputDto.Info getGamerInfo(Long gamerId){

        Gamer gamer = gamerRepository.findById(gamerId)
                .orElseThrow(() -> new GamerException(GamerErrorCode.NOT_EXIST_GAMER));

        return GamerInfoOutputDto.Info.of(gamer);
    }


    /**
     * gamer 정보 반환
     * @param searchGamer : gamer 조회 Dto
     * gamer name, nickname, race 이용한 조회 가능
     */

    @Transactional
    public List<GamerInfoOutputDto.Info> getGamerList(SearchGamer searchGamer){
        return gamerSearchRepository.searchByWhere(searchGamer.toCondition())
                .stream()
                .map(GamerInfoOutputDto.Info::of)
                .collect(Collectors.toList());
    }

    /**
     * 게이머 정보 수정
     * @param id : id(pk)
     * @param request
     * - name : 게이머 이름
     * - race : 게이머 종족
     * - nickname : 게이머 인게임 별명
     * - introduce : 게이머 자기 소개
     * id에 해당하는 gamer 존재해야 함.
     */

    @Transactional
    public GamerInfoOutputDto.Info updateGamer(
            Long id,
            UpdateGamer.Request request
    ){
        Gamer gamer = gamerRepository.findById(id)
                .orElseThrow(() -> new GamerException(GamerErrorCode.NOT_EXIST_GAMER));

        gamer.setName(request.getName());
        gamer.setRace(request.getRace());
        gamer.setNickname(request.getNickname());
        gamer.setIntroduce(request.getIntroduce());

        return GamerInfoOutputDto.Info.of(gamerRepository.save(gamer));
    }

    @Transactional
    public GamerInfoOutputDto.Info deleteGamer(Long id){
        Gamer gamer = gamerRepository.findById(id)
                .orElseThrow(() -> new GamerException(GamerErrorCode.NOT_EXIST_GAMER));
        gamerRepository.delete(gamer);

        return GamerInfoOutputDto.Info.of(gamer);
    }

    /**
     * 강의 등록
     * @param request
     * - gamerId : 게이머 Id
     * - title : 강의 제목
     * - videoUrl : 강의 url
     * - thumbnailUrl : 썸네일 url
     * - comment : 설명
     * - level : 난이도
     * - race : 종족
     * - price : 가격
     * gamerId에 해당하는 게이머가 존재하고, 강의 제목이 중복되지 않음.
     */
    @Transactional
    public CourseInfoOutputDto.Info registerCourse(
            RegisterCourse.Request request
    ){
        Gamer gamer = gamerRepository.findById(request.getGamerId())
                .orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_GAMER));

        validateRegisterCourse(request.getTitle());

        Course course = courseRepository.save(
                Course.builder()
                        .title(request.getTitle())
                        .videoUrl(request.getVideoUrl())
                        .thumbnailUrl(request.getThumbnailUrl())
                        .comment(request.getComment())
                        .level(request.getLevel())
                        .race(request.getRace())
                        .price(request.getPrice())
                        .gamer(gamer)
                        .build());

        return CourseInfoOutputDto.Info.of(course);

    }

    private void validateRegisterCourse(String title) {
        if(courseRepository.existsByTitle(title))
            throw new CourseException(CourseErrorCode.EXIST_SAME_TITLE);
    }

    /**
     *
     * @param courseId : 강의 ID
     * @param request : 수정할 강의 정보 Entity
     */
    @Transactional
    public CourseInfoOutputDto.Info updateCourseInfo(Long courseId, UpdateCourse.Request request){

        Gamer gamer = gamerRepository.findById(request.getGamerId())
                .orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_GAMER));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_COURSE));

        course.setTitle(request.getTitle());
        course.setVideoUrl(request.getVideoUrl());
        course.setThumbnailUrl(request.getThumbnailUrl());
        course.setComment(request.getComment());
        course.setLevel(request.getLevel());
        course.setRace(request.getRace());
        course.setPrice(request.getPrice());
        course.setGamer(gamer);

        return CourseInfoOutputDto.Info.of(courseRepository.save(course));
    }

    /**
     * 
     * @param courseId : 강의 id
     * @return 강의 상세 정보
     */
    @Transactional
    public CourseInfoOutputDto.Info getCourseInfo(Long courseId){

        return CourseInfoOutputDto.Info.of(
                courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_COURSE))
        );
    }

    @Transactional
    public List<CourseInfoOutputDto.Info> searchCourseList(SearchCourse searchCourse){

        return courseSearchRepository.searchByWhere(searchCourse.toCondition())
                .stream()
                .map(CourseInfoOutputDto.Info::of)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param courseId : 강의 id
     */
    @Transactional
    public CourseInfoOutputDto.Info deleteCourse(Long courseId){
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_COURSE));

        courseRepository.delete(course);

        return CourseInfoOutputDto.Info.of(course);
    }

    /**
     *
     * @param adminId : admin 로그인 ID
     * @param password : admin 비밀번호
     */
    @Transactional
    public Tokens loginAdmin(String adminId, String password){
        Admin admin = adminRepository.findByAdminId(adminId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.NOT_EXIST_ADMIN_ID));
        validateLoginAdmin(admin, password);

        return tokenService.issueAllToken(
                AdminInfo.of(
                    AdminInfo.builder()
                        .email(admin.getAdminId())
                        .role(admin.getRole().name())
                        .build().toClaims()
        ));

    }

    @Transactional
    public LogoutAdminOutputDto.Info logoutAdmin(AdminInfo adminInfo){

        refreshTokenRepository.deleteByUsername(adminInfo.getEmail());

        return LogoutAdminOutputDto.Info.of(adminInfo);
    }

    private void validateLoginAdmin(Admin admin, String password) {
        if (!admin.getPassword().equals(password)){
            throw new AdminException(AdminErrorCode.PASSWORD_IS_WRONG);
        }
    }

    /**
     *
     * @param searchMember
     * - email : email
     * - nickname : 별명
     * - point : 포인트
     */
    @Transactional
    public List<MemberSearchOutputDto.Info> searchMemberList(SearchMember searchMember){

        return memberSearchRepository.searchByWhere(searchMember.toCondition())
                .stream()
                .map(MemberSearchOutputDto.Info::of)
                .collect(Collectors.toList());

    }



}
