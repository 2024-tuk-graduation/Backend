package com.example.tukgraduation.member.dto;

import com.example.tukgraduation.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberCreateResponse {

    private String username;
    private String nickname;
    private String profileImageUrl;

    public MemberCreateResponse() {
    }

    public MemberCreateResponse(Member member) {
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.profileImageUrl = member.getProfileImageUrl();
    }
}
