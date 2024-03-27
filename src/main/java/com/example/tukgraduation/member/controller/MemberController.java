package com.example.tukgraduation.member.controller;

import com.example.tukgraduation.global.annotation.LoginRequired;
import com.example.tukgraduation.global.result.ResultCode;
import com.example.tukgraduation.global.result.ResultResponse;
import com.example.tukgraduation.member.domain.Member;
import com.example.tukgraduation.member.dto.MemberCreateRequest;
import com.example.tukgraduation.member.dto.MemberLoginRequest;
import com.example.tukgraduation.member.dto.MemberLoginResponse;
import com.example.tukgraduation.member.dto.MemberCreateResponse;
import com.example.tukgraduation.member.service.LoginService;
import com.example.tukgraduation.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;

    @Operation(summary = "회원가입", description = "회원가입 기능")
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultResponse<MemberCreateRequest>> signUp(@RequestPart String username,
                                                                      @RequestPart String password,
                                                                      @RequestPart String nickname,
                                                                      @RequestPart("file") MultipartFile multipartFile) {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(username, password, nickname);
        Member member = memberService.register(memberCreateRequest, multipartFile);
        ResultResponse<MemberCreateRequest> resultResponse = new ResultResponse<>(ResultCode.SIGN_UP_SUCCESS, new MemberCreateResponse(member));
        return ResponseEntity.status(HttpStatus.CREATED).body(resultResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(@RequestBody @Valid MemberLoginRequest loginRequest, HttpServletRequest request) {

        Member member = memberService.findUserByUsername(loginRequest.getUsername());

        if (!loginService.checkPassword(member.getUsername(), loginRequest.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        loginService.login(member.getId(), request.getSession());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_LOGIN_SUCCESS,
                MemberLoginResponse.builder()
                        .username(member.getUsername())
                        .id(member.getId())
                        .build()));
    }

    @GetMapping("/logout")
    @LoginRequired
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            loginService.logout(session);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("로그아웃 성공");
    }

}
