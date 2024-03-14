package com.example.tukgraduation.chatroom.service;

import com.example.tukgraduation.chatroom.domain.Participant;
import com.example.tukgraduation.chatroom.domain.Room;
import com.example.tukgraduation.chatroom.dto.CodeMessage;
import com.example.tukgraduation.chatroom.dto.RoomUpdateNotification;
import com.example.tukgraduation.chatroom.repository.ParticipantRepository;
import com.example.tukgraduation.chatroom.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

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

        if (room.getHostNickname() == null) {
            room.setHostNickname(nickname); // Set the first participant as the host
        }

        Participant participant = new Participant(nickname, room);
        participantRepository.save(participant);
        room.incrementParticipantCount();
        roomRepository.save(room);

        // 방의 현재 참가자 목록을 갱신
        List<String> nicknames = participantRepository.findByRoom(room).stream()
                .map(Participant::getNickname)
                .collect(Collectors.toList());

        // 웹소켓을 통해 참가자 수와 닉네임 목록을 실시간으로 방송
        messagingTemplate.convertAndSend("/sub/roomUpdate", new RoomUpdateNotification(room.getId(), nicknames.size(), room.getHostNickname(),nicknames));
        return RoomUpdateNotification.builder()
                .roomId(room.getId())
                .participantCount(room.getParticipantCount())
                .participantNicknames(nicknames)
                .hostNickname(room.getHostNickname())
                .build();
    }

    @Transactional
    public RoomUpdateNotification leaveRoom(Long roomId, String nickname) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with id: " + roomId));

        Participant leavingParticipant = participantRepository.findByRoomAndNickname(room, nickname)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found in room: " + nickname));

        participantRepository.delete(leavingParticipant);
        room.decrementParticipantCount();
        roomRepository.save(room);

        // 남아있는 참가자 목록 업데이트
        List<String> remainingNicknames = participantRepository.findByRoom(room).stream()
                .map(Participant::getNickname)
                .collect(Collectors.toList());

        RoomUpdateNotification notification = new RoomUpdateNotification(
                room.getId(),
                room.getParticipantCount(),
                room.getHostNickname(),
                remainingNicknames
        );
        broadcastRoomUpdate(notification);
        return notification;
    }

    private void broadcastRoomUpdate(RoomUpdateNotification notification) {
        messagingTemplate.convertAndSend("/sub/roomUpdate", notification);
    }

    public boolean isNicknameExists(String entranceCode, String nickname) {
        Room room = roomRepository.findByEntranceCode(entranceCode)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
        return participantRepository.findByRoomAndNickname(room, nickname).isPresent();
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String nickname = (String) headerAccessor.getSessionAttributes().get("nickname");
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");

        if(nickname != null && roomId != null) {
            RoomUpdateNotification notification = leaveRoom(roomId, nickname);
            messagingTemplate.convertAndSend("/sub/roomUpdate", notification);
        }
    }

    public void broadcastCodeFromHost(CodeMessage codeMessage) {
        messagingTemplate.convertAndSend("/sub/code", codeMessage);
    }

    public void receiveCodeFromHost(String workspaceId, CodeMessage codeMessage, boolean isHost) {
        if (isHost) {
            // 호스트인 경우에만 메시지를 처리
            broadcastCodeFromHost(codeMessage);
        }
        
    }

//    private void broadcastRoomUpdate(Room room) {
//        List<String> nicknames = participantRepository.findByRoom(room).stream()
//                .map(Participant::getNickname)
//                .collect(Collectors.toList());
//
//        messagingTemplate.convertAndSend("/sub/roomUpdate", new RoomUpdateNotification(room.getId(), room.getParticipantCount(), nicknames));
//    }

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

//    public boolean verifyEntrance(String entranceCode) {
//        return roomRepository.findByEntranceCode(entranceCode).isPresent();
//    }
//
//    public Room updateParticipantCount(Long roomId) {
//        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found."));
//        room.incrementParticipantCount();
//        return roomRepository.save(room);
//    }


}



