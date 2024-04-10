package com.example.tukgraduation.chatroom.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RoomUpdateNotification {
    private int personnelCount;
    private List<String> participantNicknames;

public RoomUpdateNotification(int personnelCount, List<String> participantNicknames) {
        this.personnelCount = personnelCount;
        this.participantNicknames = participantNicknames;
    }

}
