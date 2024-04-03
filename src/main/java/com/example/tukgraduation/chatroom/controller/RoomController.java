package com.example.tukgraduation.chatroom.controller;

import com.example.tukgraduation.chatroom.domain.Room;
import com.example.tukgraduation.chatroom.dto.*;
import com.example.tukgraduation.chatroom.service.RoomService;
import com.example.tukgraduation.global.annotation.LoginMember;
import com.example.tukgraduation.global.annotation.LoginRequired;
import com.example.tukgraduation.global.result.ResultCode;
import com.example.tukgraduation.global.result.ResultResponse;
import com.example.tukgraduation.member.domain.Member;
import com.example.tukgraduation.member.dto.MemberCreateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    // 방 생성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @LoginRequired
    public ResponseEntity<ResultResponse<RoomCreateResponse>> createRoom(
            @RequestPart("data") String jsonRoomCreateRequest,
            @LoginMember @Parameter(hidden = true) Member loginMember,
            @RequestPart(value = "pdfFiles", required = false)  List<MultipartFile> pdfFiles) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        RoomCreateRequest roomCreateRequest = mapper.readValue(jsonRoomCreateRequest, RoomCreateRequest.class);
        Room room = roomService.createRoom(roomCreateRequest, loginMember, pdfFiles);
        ResultResponse<RoomCreateResponse> resultResponse = new ResultResponse<>(ResultCode.ROOM_CREATE_SUCCESS, new RoomCreateResponse(room));
        return new ResponseEntity<>(resultResponse, HttpStatus.CREATED);
    }



    @PostMapping("/entrance")
    public ResponseEntity<?> enterRoom(@RequestBody RoomEnterRequest request,
                                       @LoginMember @Parameter(hidden = true) Member loginMember){

//        if (roomService.isNicknameExists(request.getEntranceCode(), )) {
//            return ResponseEntity.badRequest().body("이미 존재하는 닉네임입니다.");
//        }

        RoomUpdateNotification roomUpdateNotification = roomService.enterRoom(request.getEntranceCode(), loginMember);

        if (roomUpdateNotification != null) {
            return ResponseEntity.ok(roomUpdateNotification);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

    }

    @PostMapping("/leave")
    public ResponseEntity<RoomUpdateNotification> leaveRoom(@RequestBody RoomLeaveRequest request) {
        try {
            RoomUpdateNotification notification = roomService.leaveRoom(request.getRoomId(), request.getNickname());
            // 방 나가기 성공, 업데이트된 참가자 정보 포함하여 반환
            return ResponseEntity.ok(notification);
        } catch (IllegalArgumentException e) {
            // 예외 처리, 방을 찾을 수 없거나 사용자가 방에 없는 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
