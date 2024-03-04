package com.example.tukgraduation.chatroom.service;

import com.example.tukgraduation.chatroom.domain.Participant;
import com.example.tukgraduation.chatroom.domain.Room;
import com.example.tukgraduation.chatroom.repository.ParticipantRepository;
import com.example.tukgraduation.chatroom.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final ParticipantRepository participantRepository;

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
        Optional<Room> roomOptional = roomRepository.findByEntranceCode(entranceCode);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            // 입장 코드가 일치하면, 참가자 정보를 저장
            Participant participant = new Participant(nickname, room);
            participantRepository.save(participant);
            return true; // 입장 코드가 일치하므로 입장 허용
        }
        return false; // 입장 코드가 일치하지 않으므로 입장 거부
    }

    // 방 입장 검증
//    public boolean verifyEntrance(String entranceCode, String nickname) {
//        Optional<Room> roomOptional = roomRepository.findByEntranceCode(entranceCode);
//        if (roomOptional.isPresent()) {
//            Room room = roomOptional.get();
//            // 닉네임 저장 로직 추가
//            Participant participant = new Participant(nickname, room);
//            participantRepository.save(participant);
//            // 호스트인지 검증
//            return Objects.equals(room.getHostNickname(), nickname);
//        }
//        return false;
//    }

    public boolean verifyEntrance(String entranceCode) {
        return roomRepository.findByEntranceCode(entranceCode).isPresent();
    }
}

