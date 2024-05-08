package com.example.tukgraduation.chatroom.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignalingMessage {
    private String type; // "offer", "answer", "iceCandidate"
    private String content; // JSON string of SDP or ICE candidate
    private String roomId; // Identifier of the chat room
    private Integer sdpMLineIndex;
    private String sdpMid;

    public SignalingMessage(String type, String content, String roomId, Integer sdpMLineIndex, String sdpMid) {
        this.type = type;
        this.content = content;
        this.roomId = roomId;
        this.sdpMLineIndex = sdpMLineIndex;
        this.sdpMid = sdpMid;
    }


}