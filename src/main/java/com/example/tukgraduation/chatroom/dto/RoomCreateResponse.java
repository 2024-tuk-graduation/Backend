package com.example.tukgraduation.chatroom.dto;


import com.example.tukgraduation.chatroom.domain.Room;
import lombok.Builder;
import lombok.Getter;


@Getter
public class RoomCreateResponse {
    private String hostNickname;
    private String language;
    private int roomMaximumCount;
    private String entranceCode;

    public RoomCreateResponse() {
    }
    public RoomCreateResponse(Room room) {
        this.hostNickname = room.getHostNickname();
        this.language = room.getLanguage();
        this.roomMaximumCount = room.getRoomMaximumCount();
        this.entranceCode = room.getEntranceCode();
    }


}
