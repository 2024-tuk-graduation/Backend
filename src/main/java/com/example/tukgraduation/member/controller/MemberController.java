package com.example.tukgraduation.member.controller;

import com.example.tukgraduation.global.annotation.LoginRequired;
import com.example.tukgraduation.global.result.ResultCode;
import com.example.tukgraduation.global.result.ResultResponse;
import com.example.tukgraduation.member.domain.Member;
import com.example.tukgraduation.member.dto.MemberCreateRequest;
import com.example.tukgraduation.member.dto.MemberLoginRequest;
import com.example.tukgraduation.member.dto.MemberLoginResponse;
import com.example.tukgraduation.member.service.LoginService;
import com.example.tukgraduation.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<String> registration(@RequestBody @Valid MemberCreateRequest createRequest) {
        if (memberService.isDuplicatedUsername(createRequest.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }
        memberService.register(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("회원가입 성공");
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
                        .name(member.getName())
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
