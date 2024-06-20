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
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;

    public RoomController(RoomService roomService, SimpMessagingTemplate messagingTemplate) {
        this.roomService = roomService;
        this.messagingTemplate = messagingTemplate;
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
    public ResponseEntity<ResultResponse<RoomEnterResponse>> enterRoom(
            @RequestBody RoomEnterRequest request,
            @LoginMember @Parameter(hidden = true) Member loginMember){
        RoomEnterResponse roomEnterResponse = roomService.enterRoom(request.getEntranceCode(), loginMember);
        ResultResponse<RoomEnterResponse> resultResponse = new ResultResponse<>(ResultCode.ROOM_ENTER_SUCCESS, roomEnterResponse);
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
        RoomInfoResponse roomInfo = roomService.getRoomInfoWithFiles(entranceCode);
        ResultResponse<RoomInfoResponse> resultResponse = new ResultResponse<>(ResultCode.ROOM_INFO_SUCCESS, roomInfo);
        return ResponseEntity.ok(resultResponse);
    }

    @PostMapping("/changeHost")
    public ResponseEntity<ResultResponse<HostChangeResponse>> changeHost(@RequestBody HostChangeRequest request) {
        HostChangeResponse hostChangeResponse = roomService.changeHost(request.getEntranceCode(), request.getCurrentHostNickname(), request.getNewHostNickname());
        ResultResponse<HostChangeResponse> resultResponse = new ResultResponse<>(ResultCode.HOST_CHANGE_SUCCESS, hostChangeResponse);
        return ResponseEntity.ok(resultResponse);
    }
    
    @MessageMapping("/canvasdraw")
    public void handleCanvasDraw(@Payload DrawData drawData) {
        messagingTemplate.convertAndSend("/sub/canvasdraw", drawData);
    }

    @MessageMapping("/canvasdraw/color")
    public void handleCanvasDrawColor(@Payload DrawData.ColorChange colorChange) {
        messagingTemplate.convertAndSend("/sub/canvasdraw/color", colorChange);
    }

    @MessageMapping("/canvasdraw/thickness")
    public void handleCanvasDrawThickness(@Payload DrawData.ThicknessChange thicknessChange) {
        messagingTemplate.convertAndSend("/sub/canvasdraw/thickness", thicknessChange);
    }

    @MessageMapping("/canvasdraw/type")
    public void handleCanvasDrawType(@Payload DrawData.DrawTypeChange drawTypeChange) {
        messagingTemplate.convertAndSend("/sub/canvasdraw/type", drawTypeChange);
    }

    @MessageMapping("/canvasdraw/clearall")
    public void handleCanvasDrawClearAll(@Payload DrawData.ClearAll clearAll) {
        messagingTemplate.convertAndSend("/sub/canvasdraw/clearall", clearAll);
    }

    @MessageMapping("/canvasdraw/erasepart")
    public void handleCanvasDrawErasePart(@Payload DrawData.ErasePart erasePart) {
        messagingTemplate.convertAndSend("/sub/canvasdraw/erasepart", erasePart);
    }


    @MessageMapping("/canvasdraw/codeMode")
    public void handleCanvasDrawCodeMode(@Payload DrawData.CodeMode codeMode) {
        messagingTemplate.convertAndSend("/sub/canvasdraw/codeMode", codeMode);
    }

    @PostMapping("/qna")
    public ResponseEntity<ResultResponse<QnaMessage>> sendQna(@RequestBody QnaMessage qnaMessage) {
        messagingTemplate.convertAndSend("/sub/qna", qnaMessage);
        return new ResponseEntity<>(new ResultResponse<>(ResultCode.QNA_CREATE_SUCCESS, null), HttpStatus.OK);
    }
}
