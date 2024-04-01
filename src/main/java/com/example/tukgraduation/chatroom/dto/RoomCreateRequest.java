package com.example.tukgraduation.chatroom.dto;

import com.example.tukgraduation.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreateRequest {
    private String language; // 사용 언어
    private int roomMaximumCount;
}
