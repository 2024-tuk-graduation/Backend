package com.example.tukgraduation.chatroom.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class MediaStatusRequest {
    private boolean cam;
    private boolean voice;

    public  MediaStatusRequest(boolean cam, boolean voice) {
        this.cam= cam;
        this.voice = voice;
      
    }
}
