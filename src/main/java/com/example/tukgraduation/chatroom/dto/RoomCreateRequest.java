package com.example.tukgraduation.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreateRequest {
    private String language; // 사용 언어
    private String template;
    private int roomMaximumCount;
    private String roomName;

}
