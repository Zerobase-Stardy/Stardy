package com.github.backend.service;

import com.github.backend.exception.AdminException;
import com.github.backend.exception.CourseException;
import com.github.backend.exception.GamerException;
import com.github.backend.model.constants.Role;
import com.github.backend.model.dto.SearchCourse;
import com.github.backend.model.dto.SearchGamer;
import com.github.backend.model.dto.UpdateCourse;
import com.github.backend.persist.entity.Admin;
import com.github.backend.persist.entity.Course;
import com.github.backend.persist.entity.Gamer;
import com.github.backend.persist.repository.AdminRepository;
import com.github.backend.persist.repository.CourseRepository;
import com.github.backend.persist.repository.GamerRepository;
import com.github.backend.persist.repository.querydsl.CourseSearchRepository;
import com.github.backend.persist.repository.querydsl.GamerSearchRepository;
import com.github.backend.persist.repository.querydsl.condition.CourseSearchCondition;
import com.github.backend.type.AdminErrorCode;
import com.github.backend.type.CourseErrorCode;
import com.github.backend.type.GamerErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final GamerRepository gamerRepository;
    private final CourseRepository courseRepository;
    private final GamerSearchRepository gamerSearchRepository;
    private final CourseSearchRepository courseSearchRepository;


    /**
     * Admin 등록
     * @param adminId : adminId
     * @param password : adminPassword
     * 중복된 Admin Id 등록할 수 없음.
     */
    @Transactional
    public Admin registerAdmin(String adminId, String password){


        validateCreateAdmin(adminId);

        return adminRepository.save(
                Admin.builder()
                        .adminId(adminId)
                        .password(password)
                        .role(Role.ROLE_ADMIN)
                        .build()
        );
    }

    private void validateCreateAdmin(String adminId) {
        if(adminRepository.existsByAdminId(adminId))
            throw new AdminException(AdminErrorCode.EXIST_SAME_ADMIN_ID);
    }

    /**
     * Gamer 등록
     * @param name : gamerName
     * @param race : gamerRace
     * @param nickname : gamerGameNickname
     * @param introduce : gamerIntroduce
     * 중복된 nickname Gamer 등록할 수 없음.
     */
    @Transactional
    public Gamer registerGamer(
            String name,
            String race,
            String nickname,
            String introduce
    ){

        validateCreateGamer(nickname);

        return gamerRepository.save(
                Gamer.builder()
                        .name(name)
                        .race(race)
                        .nickname(nickname)
                        .introduce(introduce)
                        .build()
        );

    }

    /**
     * gamer 정보 반환
     * @param searchGamer : gamer 조회 Dto
     * gamer name, nickname, race 이용한 조회 가능
     */
    @Transactional
    public List<Gamer> getGamerList(SearchGamer searchGamer){

        return gamerSearchRepository.searchByWhere(
                searchGamer.toCondition()
        );
    }


    private void validateCreateGamer(String nickname) {
        if (gamerRepository.existsByNickname(nickname)) {
            throw new GamerException(GamerErrorCode.EXIST_SAME_GAMER_NICKNAME);
        }
    }

    /**
     * 게이머 정보 수정
     * @param id : id(pk)
     * @param name : 게이머 이름
     * @param race : 게이머 종족
     * @param nickname : 게이머 인게임 별명
     * @param introduce : 게이머 자기 소개
     * id에 해당하는 gamer 존재해야 함.
     */
    @Transactional
    public Gamer updateGamer(
            Long id,
            String name,
            String race,
            String nickname,
            String introduce
    ){
        Gamer gamer = gamerRepository.findById(id)
                .orElseThrow(() -> new GamerException(GamerErrorCode.NOT_EXIST_GAMER));

        gamer.setName(name);
        gamer.setRace(race);
        gamer.setNickname(nickname);
        gamer.setIntroduce(introduce);

        return gamerRepository.save(gamer);
    }

    @Transactional
    public void deleteGamer(Long id){
        Gamer gamer = gamerRepository.findById(id)
                .orElseThrow(() -> new GamerException(GamerErrorCode.NOT_EXIST_GAMER));

        gamerRepository.delete(gamer);
    }

    /**
     * 강의 등록
     * @param gamerId : 게이머 Id
     * @param title : 강의 제목
     * @param videoUrl : 강의 url
     * @param thumbnailUrl : 썸네일 url
     * @param comment : 설명
     * @param level : 난이도
     * @param race : 종족
     * @param price : 가격
     * gamerId에 해당하는 게이머가 존재하고, 강의 제목이 중복되지 않음.
     */
    @Transactional
    public Course registerCourse(
            Long gamerId,
            String title,
            String videoUrl,
            String thumbnailUrl,
            String comment,
            String level,
            String race,
            Long price
    ){
        Gamer gamer = gamerRepository.findById(gamerId)
                .orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_GAMER));

        validateRegisterCourse(title);

        return courseRepository.save(
                Course.builder()
                        .title(title)
                        .videoUrl(videoUrl)
                        .thumbnailUrl(thumbnailUrl)
                        .comment(comment)
                        .level(level)
                        .race(race)
                        .price(price)
                        .gamer(gamer)
                        .build()
        );
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
    public Course updateCourseInfo(Long courseId, UpdateCourse.Request request){

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

        return courseRepository.save(course);
    }

    /**
     * 
     * @param courseId : 강의 id
     * @return 강의 상세 정보
     */
    @Transactional
    public Course getCourseInfo(Long courseId){

        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_COURSE));
    }

    @Transactional
    public List<Course> searchCourseList(SearchCourse searchCourse){

        return courseSearchRepository.searchByWhere(
                searchCourse.toCondition()
        );

    }

    /**
     *
     * @param courseId : 강의 id
     */
    @Transactional
    public void deleteCourse(Long courseId){
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_COURSE));

        courseRepository.delete(course);
    }


}
