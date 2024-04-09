package com.example.tukgraduation.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RoomInfoResponse {
    private Long roomId;
    private String roomName;
    private String language;
    private int personnelCount;
    private String entranceCode;
    private int template;
    private String hostNickname; // 호스트의 닉네임
    private List<String> participantNicknames; // 참여 인원의 닉네임 목록
}
