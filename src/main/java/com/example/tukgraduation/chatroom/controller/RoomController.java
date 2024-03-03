package com.example.tukgraduation.chatroom.controller;

import com.example.tukgraduation.chatroom.domain.Room;
import com.example.tukgraduation.chatroom.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // 방 생성
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room newRoom = roomService.createRoom(room.getHostNickname());
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }

    // 방 입장
    @GetMapping("/{id}")
    public ResponseEntity<String> enterRoom(@RequestParam String entranceCode, @RequestParam String nickname) {
        boolean isHost = roomService.verifyEntrance(entranceCode, nickname);
        if (isHost) {
            return ResponseEntity.ok("호스트로 입장했습니다.");
        } else {
            return ResponseEntity.ok("게스트로 입장했습니다.");
        }
    }
}
