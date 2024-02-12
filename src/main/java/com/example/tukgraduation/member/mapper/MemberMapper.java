package com.example.tukgraduation.member.mapper;

import com.example.tukgraduation.member.repository.MemberRepository;
import com.example.tukgraduation.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.example.tukgraduation.member.domain.Member;

@Component
@RequiredArgsConstructor
public class MemberMapper {

    private final MemberRepository memberRepository;

    public Member toMemberEntity(MemberDto.MemberCreateRequest memberCreate) {
        return Member.builder()
                .username(memberCreate.getUsername())
                .password(memberCreate.getPassword())
                .build();

    }

}
