package com.example.tukgraduation.chatroom.service;

import com.example.tukgraduation.chatroom.domain.Participant;
import com.example.tukgraduation.chatroom.domain.Room;
import com.example.tukgraduation.chatroom.dto.RoomUpdateNotification;
import com.example.tukgraduation.chatroom.repository.ParticipantRepository;
import com.example.tukgraduation.chatroom.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final ParticipantRepository participantRepository;
    private final SimpMessagingTemplate messagingTemplate;


    // 방 생성
    public Room createRoom(String hostNickname) {
        Room room = Room.builder()
                .entranceCode("1234")
                .hostNickname(hostNickname)
                .build();
        return roomRepository.save(room);
    }

    // 방 입장 검증
    @Transactional
    public RoomUpdateNotification enterRoom(String entranceCode, String nickname) {
        Room room = roomRepository.findByEntranceCode(entranceCode)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with entrance code: " + entranceCode));

        Participant participant = new Participant(nickname, room);
        participantRepository.save(participant);

        // 방의 현재 참가자 목록을 갱신
        List<String> nicknames = participantRepository.findByRoom(room).stream()
                .map(Participant::getNickname)
                .collect(Collectors.toList());

        // 웹소켓을 통해 참가자 수와 닉네임 목록을 실시간으로 방송
        messagingTemplate.convertAndSend("/sub/roomUpdate", new RoomUpdateNotification(room.getId(), nicknames.size(), nicknames));
        return RoomUpdateNotification.builder()
                .roomId(room.getId())
                .participantCount(nicknames.size())
                .participantNicknames(nicknames)
                .build();
    }

    @Transactional
    public void leaveRoom(Long roomId, String nickname) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with id: " + roomId));

        participantRepository.findByRoomAndNickname(room, nickname)
                .ifPresent(participant -> {
                    participantRepository.delete(participant);
                    room.decrementParticipantCount();
                    roomRepository.save(room);
                    broadcastRoomUpdate(room);
                });
    }

    private void broadcastRoomUpdate(Room room) {
        List<String> nicknames = participantRepository.findByRoom(room).stream()
                .map(Participant::getNickname)
                .collect(Collectors.toList());

        messagingTemplate.convertAndSend("/sub/roomUpdate", new RoomUpdateNotification(room.getId(), room.getParticipantCount(), nicknames));
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

    public Room updateParticipantCount(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found."));
        room.incrementParticipantCount();
        return roomRepository.save(room);
    }


}



