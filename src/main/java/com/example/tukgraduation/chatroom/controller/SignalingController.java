package com.example.tukgraduation.chatroom.controller;

import com.example.tukgraduation.chatroom.dto.SignalingMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SignalingController {

    @MessageMapping("/peer/offer/{roomId}")
    @SendTo("/pub/peer/offer/{roomId}")
    public SignalingMessage handleOffer(@Payload SignalingMessage offer, @DestinationVariable("roomId") String roomId) {
        log.info("[OFFER] Room {} : {}", roomId, offer.getContent());
        return offer;
    }
    
    @MessageMapping("/peer/answer/{roomId}")
    @SendTo("/pub/peer/answer/{roomId}")
    public SignalingMessage handleAnswer(@Payload SignalingMessage answer, @DestinationVariable("roomId") String roomId) {
        log.info("[ANSWER] Room {} : {}", roomId, answer.getContent());
        return answer;
    }

    @MessageMapping("/peer/iceCandidate/{roomId}")
    @SendTo("/pub/peer/iceCandidate/{roomId}")
    public SignalingMessage handleIceCandidate(@Payload SignalingMessage candidate, @DestinationVariable("roomId") String roomId) {
        log.info("[ICE CANDIDATE] Room {} : {}", roomId, candidate.getContent());
        return candidate;
    }
}
