package com.example.tukgraduation.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomLeaveRequest {
    private Long roomId;
    private String nickname;

}
