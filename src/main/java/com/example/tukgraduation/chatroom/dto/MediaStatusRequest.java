package com.example.tukgraduation.chatroom.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class MediaStatusRequest {
    private boolean cam;
    private boolean voice;
}
