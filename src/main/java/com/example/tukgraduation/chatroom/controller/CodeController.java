package com.example.tukgraduation.chatroom.controller;

import com.example.tukgraduation.chatroom.dto.CodeMessage;
import com.example.tukgraduation.chatroom.dto.CompilerRequest;
import com.example.tukgraduation.chatroom.dto.CompilerResponse;
import com.example.tukgraduation.chatroom.service.CodeService;
import com.example.tukgraduation.global.result.ResultCode;
import com.example.tukgraduation.global.result.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/codes")
public class CodeController {

    private final SimpMessagingTemplate messagingTemplate;
    private final CodeService codeService;

    public CodeController(SimpMessagingTemplate messagingTemplate, CodeService codeService) {
        this.messagingTemplate = messagingTemplate;
        this.codeService = codeService;
    }

    @MessageMapping("code")
    @SendTo("/pub/code")
    public void handleCodeSubmission(CodeMessage codeMessage) {
        // 메시지를 받고, 처리한 뒤 다시 해당 워크스페이스 구독자에게 메시지를 전송합니다.
        messagingTemplate.convertAndSend("/sub/code", codeMessage);
    }

    @PostMapping()
    public ResponseEntity<ResultResponse<CompilerResponse>> compile(
            @RequestBody CompilerRequest request) {

        ResultResponse<CompilerResponse> response = new ResultResponse<>(ResultCode.COMPILE_SUCCESS, codeService.getCompileResponse(request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
