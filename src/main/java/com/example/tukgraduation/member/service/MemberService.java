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

    public Member register(MemberCreateRequest requestDto, MultipartFile multipartFile) {

        String profileImageUrl;
        if (multipartFile == null) {
            profileImageUrl = "https://tukgraduation.s3.ap-northeast-2.amazonaws.com/default_folder/defualt_profile.svg";
        }
        else {
            profileImageUrl = amazonS3Service.upload(requestDto.getUsername(), multipartFile);
        }
        Member member = Member.builder()
                .username(requestDto.getUsername())
                .nickname(requestDto.getNickname())
                .password(requestDto.getPassword())
                .profileImageUrl(profileImageUrl)
                .build();
        return memberRepository.save(member);
    }

    public boolean isDuplicatedUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    public Member findUserById(Long userId) {
        return memberRepository.findById(userId).orElseThrow();
    }

    public Member findUserByUsername(String username) {
        return memberRepository.findByUsername(username).orElseThrow();
    }
}
