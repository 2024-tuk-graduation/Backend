package com.example.tukgraduation.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {
    private String roomName; // 방 이름
    private String entranceCode; // 입장 코드
    private String nickname; // 사용자 닉네임
}