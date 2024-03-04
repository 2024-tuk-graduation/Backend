package com.example.tukgraduation.chatroom.controller;

import com.example.tukgraduation.chatroom.domain.Room;
import com.example.tukgraduation.chatroom.dto.RoomEnterRequest;
import com.example.tukgraduation.chatroom.service.RoomService;
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
    public ResponseEntity<String> enterRoom(@RequestBody RoomEnterRequest request) {
        if (roomService.enterRoom(request.getEntranceCode(), request.getNickname())) {
            // 입장 코드 일치 시 입장 처리
            return ResponseEntity.ok("방에 입장했습니다.");
        } else {
            // 입장 코드 불일치 시 거부 메시지
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("입장 코드가 올바르지 않습니다.");
        }
    }
}
