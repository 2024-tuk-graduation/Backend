package com.example.tukgraduation.chatroom.service;

import com.example.tukgraduation.chatroom.domain.Room;
import com.example.tukgraduation.chatroom.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    // 방 생성
    public Room createRoom(String hostNickname) {
        Room room = Room.builder()
                .entranceCode("1234")
                .hostNickname(hostNickname)
                .build();
        return roomRepository.save(room);
    }

    // 방 입장 검증
    public boolean enterRoom(String entranceCode, String nickname) {
        Optional<Room> room = roomRepository.findAll().stream()
                .filter(r -> r.getEntranceCode().equals(entranceCode))
                .findFirst();

        if (room.isPresent()) {
            // 호스트인 경우 true 반환, 아니면 false
            return room.get().getHostNickname().equals(nickname);
        }

        return false;
    }

    // 방 입장 검증
    public boolean verifyEntrance(String entranceCode, String nickname) {
        return roomRepository.findAll().stream()
                .anyMatch(room -> Objects.equals(entranceCode, room.getEntranceCode()) &&
                        Objects.equals(nickname, room.getHostNickname()));
    }
}

