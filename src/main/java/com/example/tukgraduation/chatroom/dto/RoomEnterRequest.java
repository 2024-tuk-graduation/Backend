package com.example.tukgraduation.chatroom.dto;

import com.example.tukgraduation.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomEnterRequest {
    private String entranceCode;
}
