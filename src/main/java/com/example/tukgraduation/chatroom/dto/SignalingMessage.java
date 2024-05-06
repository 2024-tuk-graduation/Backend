package com.example.tukgraduation.chatroom.dto;

import lombok.Getter;

@Getter
public class SignalingMessage {
    private String type; // "offer", "answer", "iceCandidate"
    private String content; // JSON string of SDP or ICE candidate
    private String roomId; // Identifier of the chat room

    public SignalingMessage(String type, String content, String roomId) {
        this.type = type;
        this.content = content;
        this.roomId = roomId;
    }
}