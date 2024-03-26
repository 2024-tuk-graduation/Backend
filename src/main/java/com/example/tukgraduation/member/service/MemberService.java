package com.example.tukgraduation.member.service;

import com.example.tukgraduation.global.config.BaseEntity;
import com.example.tukgraduation.image.service.AmazonS3Service;
import com.example.tukgraduation.member.domain.Member;
import com.example.tukgraduation.member.dto.MemberCreateRequest;
import com.example.tukgraduation.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberService extends BaseEntity {

    private final MemberRepository memberRepository;
    private final AmazonS3Service amazonS3Service;

    public boolean isDuplicatedUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    public Member register(MemberCreateRequest requestDto, MultipartFile multipartFile) {
        String profileImageUrl = amazonS3Service.upload(requestDto.getUsername(), multipartFile);
        Member member = Member.builder()
                .username(requestDto.getUsername())
                .nickname(requestDto.getNickname())
                .password(requestDto.getPassword())
                .profileImageUrl(profileImageUrl)
                .build();
        return memberRepository.save(member);
    }

    public Member findUserById(Long userId) {
        return memberRepository.findById(userId).orElseThrow();
    }

    public Member findUserByUsername(String username) {
        return memberRepository.findByUsername(username).orElseThrow();
    }
}
