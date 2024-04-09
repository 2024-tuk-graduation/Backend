package com.example.tukgraduation.chatroom.controller;

import com.example.tukgraduation.chatroom.dto.*;
import com.example.tukgraduation.chatroom.service.RoomService;
import com.example.tukgraduation.global.annotation.LoginMember;
import com.example.tukgraduation.global.annotation.LoginRequired;
import com.example.tukgraduation.global.result.ResultCode;
import com.example.tukgraduation.global.result.ResultResponse;
import com.example.tukgraduation.member.domain.Member;
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
            @RequestPart(value = "uploadFiles", required = false)  List<MultipartFile> uploadFiles) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        RoomCreateRequest roomCreateRequest = mapper.readValue(jsonRoomCreateRequest, RoomCreateRequest.class);
        RoomCreateResponse roomCreateResponse = roomService.createRoom(roomCreateRequest, loginMember, uploadFiles);

        RoomCreateResponse.Entrance response = new RoomCreateResponse.Entrance(roomCreateResponse.getEntranceCode());
        ResultResponse<RoomCreateResponse> resultResponse = new ResultResponse<>(ResultCode.ROOM_CREATE_SUCCESS, response);
        return new ResponseEntity<>(resultResponse, HttpStatus.CREATED);
    }



    @PostMapping("/entrance")
    @LoginRequired
    public ResponseEntity<ResultResponse<RoomUpdateNotification>> enterRoom(
            @RequestBody RoomEnterRequest request,
            @LoginMember @Parameter(hidden = true) Member loginMember){
        RoomUpdateNotification roomUpdateNotification = roomService.enterRoom(request.getEntranceCode(), loginMember);
        ResultResponse<RoomUpdateNotification> resultResponse = new ResultResponse<>(ResultCode.ROOM_ENTER_SUCCESS, roomUpdateNotification);
        return new ResponseEntity<>(resultResponse, HttpStatus.OK);
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

    @GetMapping("/{entranceCode}")
    public ResponseEntity<ResultResponse<RoomInfoResponse>> getRoomInfoByEntranceCode(@PathVariable String entranceCode) {
        RoomInfoResponse roomInfo = roomService.getRoomInfoByEntranceCode(entranceCode);
        ResultResponse<RoomInfoResponse> resultResponse = new ResultResponse<>(ResultCode.ROOM_INFO_SUCCESS, roomInfo);
        return ResponseEntity.ok(resultResponse);
    }
}
