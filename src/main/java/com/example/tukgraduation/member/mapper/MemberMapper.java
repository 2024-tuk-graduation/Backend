package com.example.tukgraduation.member.mapper;

import com.example.tukgraduation.member.dto.MemberCreateRequest;
import com.example.tukgraduation.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.example.tukgraduation.member.domain.Member;

@Component
@RequiredArgsConstructor
public class MemberMapper {

    private final MemberRepository memberRepository;

    public Member toMemberEntity(MemberCreateRequest memberCreate) {
        return Member.builder()
                .username(memberCreate.getUsername())
                .password(memberCreate.getPassword())
                .name(memberCreate.getName())
                .build();

    }

}
