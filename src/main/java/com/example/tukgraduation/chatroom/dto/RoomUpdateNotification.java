package com.example.tukgraduation.chatroom.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RoomUpdateNotification {
    private Long roomId;
    private int participantCount;
    private String hostNickname;
    private List<String> participantNicknames;

    // 생성자를 public으로 선언
    public RoomUpdateNotification(Long roomId, int participantCount, String  hostNickname, List<String> participantNicknames) {
        this.roomId = roomId;
        this.participantCount = participantCount;
        this.participantNicknames = participantNicknames;
        this.hostNickname = hostNickname;
    }
}
