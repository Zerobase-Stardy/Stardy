package com.github.backend.service.admin.impl;

import com.github.backend.dto.admin.LogoutAdminOutputDto;
import com.github.backend.dto.admin.RegisterAdminOutputDto;
import com.github.backend.dto.common.AdminInfo;
import com.github.backend.dto.course.SearchCourse;
import com.github.backend.dto.member.MemberSearchOutputDto;
import com.github.backend.dto.member.SearchMember;
import com.github.backend.exception.member.MemberException;
import com.github.backend.exception.member.code.MemberErrorCode;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.member.repository.MemberRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdminService {


    private final AdminRepository adminRepository;
    private final GamerRepository gamerRepository;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final GamerSearchRepository gamerSearchRepository;
    private final CourseSearchRepository courseSearchRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberSearchRepository memberSearchRepository;

    private final TokenService tokenService;


    /**
     * Admin ??????
     * @param adminId : adminId
     * @param password : adminPassword
     * ????????? Admin Id ????????? ??? ??????.
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
     * Gamer ??????
     * @param request
     * - name : ????????? ??????
     * - race : ??????
     * - nickname : ????????? ????????? ??????
     * - introduce : ????????????
     * ????????? nickname Gamer ????????? ??? ??????.
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
     * @param gamerId : ????????? id
     * @return ????????? ?????? ??????
     */
    @Transactional
    public GamerInfoOutputDto.Info getGamerInfo(Long gamerId){

        Gamer gamer = gamerRepository.findById(gamerId)
                .orElseThrow(() -> new GamerException(GamerErrorCode.NOT_EXIST_GAMER));

        return GamerInfoOutputDto.Info.of(gamer);
    }


    /**
     * gamer ?????? ??????
     * @param searchGamer : gamer ?????? Dto
     * gamer name, nickname, race ????????? ?????? ??????
     */

    @Transactional
    public Page<GamerInfoOutputDto.Info> getGamerList(SearchGamer searchGamer, Pageable pageable){

        return gamerSearchRepository.searchByWhere(searchGamer.toCondition(), pageable)
                .map(GamerInfoOutputDto.Info::of);
    }

    /**
     * ????????? ?????? ??????
     * @param id : id(pk)
     * @param request
     * - name : ????????? ??????
     * - race : ????????? ??????
     * - nickname : ????????? ????????? ??????
     * - introduce : ????????? ?????? ??????
     * id??? ???????????? gamer ???????????? ???.
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
     * ?????? ??????
     * @param request
     * - gamerId : ????????? Id
     * - title : ?????? ??????
     * - videoUrl : ?????? url
     * - thumbnailUrl : ????????? url
     * - comment : ??????
     * - level : ?????????
     * - race : ??????
     * - price : ??????
     * gamerId??? ???????????? ???????????? ????????????, ?????? ????????? ???????????? ??????.
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
     * @param courseId : ?????? ID
     * @param request : ????????? ?????? ?????? Entity
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
     * @param courseId : ?????? id
     * @return ?????? ?????? ??????
     */
    @Transactional
    public CourseInfoOutputDto.Info getCourseInfo(Long courseId){

        return CourseInfoOutputDto.Info.of(
                courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_COURSE))
        );
    }

    @Transactional
    public Page<CourseInfoOutputDto.Info> searchCourseList(SearchCourse searchCourse, Pageable pageable){

        return courseSearchRepository.searchByWhere(searchCourse.toCondition(), pageable)
                .map(CourseInfoOutputDto.Info::of);

    }

    /**
     *
     * @param courseId : ?????? id
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
     * @param adminId : admin ????????? ID
     * @param password : admin ????????????
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
     * - nickname : ??????
     * - point : ?????????
     */
    @Transactional
    public Page<MemberSearchOutputDto.Info> searchMemberList(SearchMember searchMember, Pageable pageable){

        return memberSearchRepository.searchByWhere(searchMember.toCondition(), pageable)
                .map(MemberSearchOutputDto.Info::of);

    }


    @Transactional
    public MemberSearchOutputDto.Info memberNicknameChange(Long memberId, String nickname){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXISTS));

        member.changeNickname(nickname);

        return MemberSearchOutputDto.Info.of(member);

    }



}
