package com.example.tukgraduation.chatroom.dto;

import com.example.tukgraduation.chatroom.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    private CodeUrls codeUrls;
    private List<String> pdfUrls;

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

    public RoomInfoResponse(Room room, List<String> pdfUrls, List<String> codeUrls) {
        this.roomName = room.getRoomName();
        this.hostNickname = room.getHostNickname();
        this.personnelCount = room.getPersonnelCount();
        this.entranceCode = room.getEntranceCode();
        this.template = room.getTemplate();
        this.pdfUrls = pdfUrls;
        this.codeUrls = new CodeUrls(room.getLanguage(), codeUrls);
    }

}
