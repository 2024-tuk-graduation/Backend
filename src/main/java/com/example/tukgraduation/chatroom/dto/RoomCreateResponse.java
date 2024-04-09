package com.example.tukgraduation.chatroom.dto;


import com.example.tukgraduation.chatroom.domain.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class RoomCreateResponse {
    private String hostNickname;
    private int personnelCount;
    private String entranceCode;
    private int template;
    private List<String> pdfUrls;
    private String roomName;
    private CodeUrls codeUrls;
    public RoomCreateResponse() {
    }

    @Getter
    @Setter
    public static class CodeUrls {
        private String language;
        private List<String> urls; // 코드 파일 URL 리스트

        // 생성자, getter, setter
        public CodeUrls(String language, List<String> urls) {
            this.language = language;
            this.urls = urls;
        }
    }

    public RoomCreateResponse(Room room, List<String> pdfUrls, List<String> codeUrls) {
        this.roomName = room.getRoomName();
        this.hostNickname = room.getHostNickname();
        this.personnelCount = room.getPersonnelCount();
        this.entranceCode = room.getEntranceCode();
        this.template = room.getTemplate();
        this.pdfUrls = pdfUrls;
        this.codeUrls = new CodeUrls(room.getLanguage(), codeUrls);
    }


}
