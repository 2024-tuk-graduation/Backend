package com.example.tukgraduation.chatroom.controller;

import com.example.tukgraduation.chatroom.dto.MediaStatusRequest;
import com.example.tukgraduation.chatroom.dto.SignalingMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SignalingController {


    private final SimpMessagingTemplate messagingTemplate;

    public SignalingController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @MessageMapping("/peer/offer/{roomId}")
    @SendTo("/sub/peer/offer/{roomId}")
    public SignalingMessage handleOffer(@Payload SignalingMessage offer, @DestinationVariable("roomId") String roomId) {
        log.info("[OFFER] Room {} : {}", roomId, offer.getContent());
        return offer;
    }
    
    @MessageMapping("/peer/answer/{roomId}")
    @SendTo("/sub/peer/answer/{roomId}")
    public SignalingMessage handleAnswer(@Payload SignalingMessage answer, @DestinationVariable("roomId") String roomId) {
        log.info("[ANSWER] Room {} : {}", roomId, answer.getContent());
        return answer;
    }

    @MessageMapping("/peer/iceCandidate/{roomId}")
    @SendTo("/sub/peer/iceCandidate/{roomId}")
    public SignalingMessage handleIceCandidate(@Payload SignalingMessage candidate, @DestinationVariable("roomId") String roomId) {
        log.info("[ICE CANDIDATE] Room {} : {}", roomId, candidate.getContent());
        return candidate;
    }

    @MessageMapping("/media/status/{roomId}/{nickname}")
    @SendTo("/sub/media/status/{roomId}/{nickname}")
    public void handleMediaStatus(@Payload MediaStatusRequest status, @DestinationVariable("roomId") String roomId, @DestinationVariable("nickname") String nickname) {
        messagingTemplate.convertAndSend("/sub/media/status/" + roomId, status);
    }

}
