package com.github.backend.web.admin;

import com.github.backend.dto.admin.RegisterAdminOutputDto;
import com.github.backend.dto.course.SearchCourse;
import com.github.backend.dto.member.MemberSearchOutputDto;
import com.github.backend.dto.member.SearchMember;
import com.github.backend.dto.member.UpdateMemberNickname;
import com.github.backend.dto.course.CourseInfoOutputDto;
import com.github.backend.dto.admin.CreateAdmin;
import com.github.backend.dto.course.RegisterCourse;
import com.github.backend.dto.gamer.*;
import com.github.backend.dto.course.UpdateCourse;
import com.github.backend.dto.common.Result;
import com.github.backend.service.admin.impl.AdminService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-management")
public class AdminController {

    private final AdminService adminService;

    @ApiOperation(
            value = "관리자 등록",
            notes = "관리자를 등록한다."
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "adminId"
                            , value = "관리자 아이디"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "password"
                            , value = "관리자 패스워드"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),
            })
    @PostMapping("/admins")
    public ResponseEntity<Result<?>> registerAdmin(
            @RequestBody @Valid CreateAdmin.Request request
    ) {
        RegisterAdminOutputDto.Info adminInfo = adminService.registerAdmin(
                request.getAdminId(),
                request.getPassword()
        );

        // convert Entity to DTO
        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(adminInfo)
                        .build()
        );
    }

    @ApiOperation(
            value = "게이머 등록",
            notes = "게이머를 등록한다."
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "name"
                            , value = "게이머 이름"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "race"
                            , value = "게이머 종족"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "nickname"
                            , value = "게이머 게임 내 이름"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "introduce"
                            , value = "게이머 자기 소개"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),
            })
    @PostMapping("/gamers")
    public ResponseEntity<Result<?>> registerGamer(
            @RequestBody @Valid RegisterGamer.Request request
    ) {
        RegisterGamerOutputDto.Info gamerInfo = adminService.registerGamer(request);

        // convert Entity to DTO
        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(gamerInfo)
                        .build()
        );
    }

    @ApiOperation(
            value = "게이머 상세 조회",
            notes = "게이머 ID를 이용해 정보를 상세 조회한다.")
    @ApiImplicitParam (
            name ="gamerId"
            ,value ="게이머 식별자(PK)"
            ,required =true
            ,dataType ="string"
            ,paramType ="path"
            ,defaultValue ="None"
    )
    @GetMapping("/gamers/{gamerId}")
    public ResponseEntity<Result<?>> getGamerInfo(
            @PathVariable("gamerId") Long gamerId){

        GamerInfoOutputDto.Info gamerInfo = adminService.getGamerInfo(gamerId);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(gamerInfo)
                        .build()
        );
    }


    @ApiOperation(
            value = "게이머들 조회",
            notes = "게이머의 이름, 닉네임, 종족을 통해 검색한다.."
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "name"
                            , value = "게이머 이름"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "race"
                            , value = "게이머 종족"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "nickname"
                            , value = "게이머 게임 내 이름"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    )
            })
    @GetMapping("/gamers")
    public ResponseEntity<Result<?>> getGamerList(
            SearchGamer searchGamer,
            @PageableDefault(size=10, sort="id", direction = Sort.Direction.ASC)
                    Pageable pageable)
    {

        Page<GamerInfoOutputDto.Info> gamersInfo = adminService.getGamerList(searchGamer, pageable);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(gamersInfo)
                        .build()
        );
    }

    @ApiOperation(
            value = "게이머를 수정한다.",
            notes = "게이머를 수정한다."
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "gamerId"
                            , value = "게이머 식별자(PK)"
                            , required = true
                            , dataType = "string"
                            , paramType = "path"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "name"
                            , value = "게이머 이름"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "race"
                            , value = "게이머 종족"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "nickname"
                            , value = "게이머 게임 내 이름"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "introduce"
                            , value = "게이머 자기 소개"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),
            })
    @PutMapping("/gamers/{gamerId}")
    public ResponseEntity<Result<?>> updateGamerInfo(
            @PathVariable  Long gamerId,
            @RequestBody @Valid UpdateGamer.Request request
    ){
        GamerInfoOutputDto.Info gamerInfo = adminService.updateGamer(
                gamerId,
                request
        );

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(gamerInfo)
                        .build()
        );
    }

    @ApiOperation(
            value = "게이머 정보를 삭제한다",
            notes = "게이머 ID를 이용해 게이머 정보를 삭제한다..")
    @ApiImplicitParam (
            name ="gamerId"
            ,value ="게이머 식별자(PK)"
            ,required =true
            ,dataType ="string"
            ,paramType ="path"
            ,defaultValue ="None"
    )
    @DeleteMapping("/gamers/{gamerId}")
    public ResponseEntity<Result<?>> deleteGamerInfo(
            @PathVariable("gamerId") @Valid Long gamerId
    ){
        GamerInfoOutputDto.Info gamerInfo = adminService.deleteGamer(gamerId);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(gamerInfo)
                        .build()
        );
    }



    @ApiOperation(
            value = "강의를 등록한다.",
            notes = "강의를 등록한다."
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "title"
                            , value = "강의명"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "videoUrl"
                            , value = "강의 Url"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "thumbnailUrl"
                            , value = "강의 썸네일 Url"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "comment"
                            , value = "강의 내용"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "level"
                            , value = "강의 난이도"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "race"
                            , value = "강의 종족"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "price"
                            , value = "강의 가격"
                            , required = true
                            , dataType = "Long"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),
            })
    @PostMapping("/courses")
    public ResponseEntity<Result<?>> registerCourse(
            @RequestBody @Valid RegisterCourse.Request request
    ){
        CourseInfoOutputDto.Info courseInfo = adminService.registerCourse(request);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(courseInfo)
                        .build()
        );
    }


    @ApiOperation(
            value = "강의들을 검색한다.",
            notes = "강의의 제목, 난이도, 종족을 통해 검색한다."
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "title"
                            , value = "강의명"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "level"
                            , value = "강의 난이도"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "race"
                            , value = "강의 종족"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    )
            })
    @GetMapping("/courses")
    public ResponseEntity<Result<?>> getCourseList(SearchCourse searchCourse,
                                                   @PageableDefault(size=10, sort="id", direction = Sort.Direction.ASC) Pageable pageable){

        Page<CourseInfoOutputDto.Info> course = adminService.searchCourseList(searchCourse, pageable);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(course)
                        .build()
        );
    }

    @ApiOperation(
            value = "강의 상세 정보를 조회한다.",
            notes = "강의 ID를 이용하여 상세 정보를 조회한다.")
    @ApiImplicitParam (
            name ="courseId"
            ,value ="강의 식별자(PK)"
            ,required =true
            ,dataType ="string"
            ,paramType ="path"
            ,defaultValue ="None"
    )
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<Result<?>> getCourseInfo(
            @PathVariable("courseId") @Valid Long courseId
    ){
        return ResponseEntity.ok()
                .body(
                    Result.builder()
                            .status(200)
                            .success(true)
                            .data(adminService.getCourseInfo(courseId))
                            .build()
                );
    }

    @ApiOperation(
            value = "강의를 수정한다.",
            notes = "강의를 수정한다."
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam (
                            name ="courseId"
                            ,value ="강의 식별자(PK)"
                            ,required =true
                            ,dataType ="string"
                            ,paramType ="path"
                            ,defaultValue ="None"
                    ),

                    @ApiImplicitParam(
                            name = "title"
                            , value = "강의명"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "videoUrl"
                            , value = "강의 Url"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "thumbnailUrl"
                            , value = "강의 썸네일 Url"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "comment"
                            , value = "강의 내용"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "level"
                            , value = "강의 난이도"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "race"
                            , value = "강의 종족"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "price"
                            , value = "강의 가격"
                            , required = true
                            , dataType = "Long"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),
            })
    @PutMapping("/courses/{courseId}")
    public ResponseEntity<Result<?>> updateCourseInfo(
            @PathVariable("courseId") @Valid Long courseId,
            @RequestBody UpdateCourse.Request request
    ){

        CourseInfoOutputDto.Info courseInfo = adminService.updateCourseInfo(courseId, request);
        return ResponseEntity.ok()
                .body(
                        Result.builder()
                                .status(200)
                                .success(true)
                                .data(courseInfo)
                                .build()
                );
    }

    @ApiOperation(
            value = "강의 정보를 삭제한다.",
            notes = "강의 ID를 이용하여 정보를 삭제한다.")
    @ApiImplicitParam (
            name ="courseId"
            ,value ="강의 식별자(PK)"
            ,required =true
            ,dataType ="string"
            ,paramType ="path"
            ,defaultValue ="None"
    )
    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<Result<?>> deleteCourseInfo(
            @PathVariable("courseId") @Valid Long courseId
    ){
        CourseInfoOutputDto.Info courseInfo = adminService.deleteCourse(courseId);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(courseInfo)
                        .build()
        );
    }


    @ApiOperation(
            value = "회원 정보를 검색한다.",
            notes = "회원 이메일, 닉네임, 포인트로 검색한다"
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam (
                            name ="email"
                            ,value ="회원 email"
                            ,required =false
                            ,dataType ="string"
                            ,paramType ="path"
                            ,defaultValue ="None"
                    ),

                    @ApiImplicitParam(
                            name = "nickname"
                            , value = "회원 닉네임"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    ),

                    @ApiImplicitParam(
                            name = "point"
                            , value = "회원 point"
                            , required = true
                            , dataType = "Long"
                            , paramType = "query"
                            , defaultValue = "None"
                    )
            })
    @GetMapping("/members")
    public ResponseEntity<Result<?>> getMembers(
            SearchMember searchMember,
            @PageableDefault(size=10, sort="id", direction = Sort.Direction.ASC)
                    Pageable pageable)
    {
        Page<MemberSearchOutputDto.Info> members = adminService.searchMemberList(searchMember, pageable);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(members)
                        .build()
        );
    }

    @ApiOperation(
            value = "회원 닉네임을 수정한다.",
            notes = "회원 닉네임을 수정한다."
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam (
                            name ="memberId"
                            ,value ="회원 ID"
                            ,required =true
                            ,dataType ="string"
                            ,paramType ="path"
                            ,defaultValue ="None"
                    ),

                    @ApiImplicitParam(
                            name = "nickname"
                            , value = "회원 닉네임"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    )
            })
    @PatchMapping("/members/nickname/{memberId}")
    public ResponseEntity<Result<?>> updateMemberNickname(
            @PathVariable("memberId") @Valid Long memberId,
            @RequestBody UpdateMemberNickname.Request request
    ){
        MemberSearchOutputDto.Info memberInfo = adminService.memberNicknameChange(memberId, request.getNickname());

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(memberInfo)
                        .build()
        );
    }


}
