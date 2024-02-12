package com.example.tukgraduation.member.controller;

import com.example.tukgraduation.member.service.MemberService;
import com.example.tukgraduation.member.domain.Member;
import com.example.tukgraduation.member.dto.MemberDto;
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

    @PostMapping
    public ResponseEntity<String> registration(@RequestBody @Valid MemberDto.MemberCreateRequest createRequest) {
        if (memberService.isDuplicatedUsername(createRequest.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }
        memberService.register(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("사용자 등록 성공");
    }

}
