package com.example.tukgraduation.chatroom.controller;

import com.example.tukgraduation.chatroom.dto.CodeMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class CodeController {
    private final SimpMessagingTemplate messagingTemplate;

    public CodeController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("code")
    @SendTo("/pub/code")
    public void handleCodeSubmission(CodeMessage codeMessage) {
        // 메시지를 받고, 처리한 뒤 다시 해당 워크스페이스 구독자에게 메시지를 전송합니다.
        messagingTemplate.convertAndSend("/sub/code", codeMessage);
    }

}
