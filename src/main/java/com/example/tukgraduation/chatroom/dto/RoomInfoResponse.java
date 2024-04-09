package com.example.tukgraduation.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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

}
