package com.example.tukgraduation.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomEnterRequest {
    private String entranceCode;
    private String nickname;
}
