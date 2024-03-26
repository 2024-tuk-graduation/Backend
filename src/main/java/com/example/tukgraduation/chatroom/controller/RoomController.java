package com.example.tukgraduation.chatroom.controller;

import com.example.tukgraduation.chatroom.domain.Room;
import com.example.tukgraduation.chatroom.dto.RoomEnterRequest;
import com.example.tukgraduation.chatroom.dto.RoomLeaveRequest;
import com.example.tukgraduation.chatroom.dto.RoomUpdateNotification;
import com.example.tukgraduation.chatroom.service.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    // 방 생성
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room newRoom = roomService.createRoom(room.getHostNickname());
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }

    // 방 입장
//    @PostMapping("/entrance")
//    public ResponseEntity<String> enterRoom(@RequestBody RoomEnterRequest request) {
//        boolean isHost = roomService.verifyEntrance(request.getEntranceCode(), request.getNickname());
//        if (isHost) {
//            return ResponseEntity.ok("호스트로 입장했습니다.");
//        } else {
//            return ResponseEntity.ok("게스트로 입장했습니다.");
//        }
//    }

    @PostMapping("/entrance")
    public ResponseEntity<?> enterRoom(@RequestBody RoomEnterRequest request) {

        if (roomService.isNicknameExists(request.getEntranceCode(), request.getNickname())) {
            return ResponseEntity.badRequest().body("이미 존재하는 닉네임입니다.");
        }

        RoomUpdateNotification roomUpdateNotification = roomService.enterRoom(request.getEntranceCode(), request.getNickname());

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
