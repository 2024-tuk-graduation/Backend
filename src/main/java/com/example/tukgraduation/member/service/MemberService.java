package com.example.tukgraduation.member.service;

import com.example.tukgraduation.global.config.BaseEntity;
import com.example.tukgraduation.member.domain.Member;
import com.example.tukgraduation.member.dto.MemberDto;
import com.example.tukgraduation.member.mapper.MemberMapper;
import com.example.tukgraduation.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService extends BaseEntity {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public boolean isDuplicatedUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    public Member register(MemberDto.MemberCreateRequest requestDto) {
        Member member = memberMapper.toMemberEntity(requestDto);
        return memberRepository.save(member);
    }

    public Member findUserById(Long userId) {
        return memberRepository.findById(userId).orElseThrow();
    }

}
