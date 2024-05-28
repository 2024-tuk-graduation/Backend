package com.example.tukgraduation.chatroom.service;

import com.example.tukgraduation.chatroom.domain.Participant;
import com.example.tukgraduation.chatroom.domain.Room;
import com.example.tukgraduation.chatroom.dto.*;
import com.example.tukgraduation.chatroom.repository.ParticipantRepository;
import com.example.tukgraduation.chatroom.repository.RoomRepository;
import com.example.tukgraduation.global.annotation.LoginMember;
import com.example.tukgraduation.member.domain.Member;
import com.example.tukgraduation.uploadfile.domain.UploadFile;
import com.example.tukgraduation.uploadfile.repository.UploadFileRepository;
import com.example.tukgraduation.uploadfile.service.UploadFileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ParticipantRepository participantRepository;
    private final UploadFileService uploadFileService;
    private final UploadFileRepository uploadFileRepository;



    // 방 생성
    @Transactional
    public RoomCreateResponse createRoom(RoomCreateRequest request, @LoginMember Member loginMember, List<MultipartFile> uploadFiles) {

        String entranceCode = RandomStringUtils.randomAlphanumeric(6);
        Room room = Room.builder()
                .hostNickname(loginMember.getNickname())
                .roomName(request.getRoomName())
                .language(request.getLanguage())
                .personnelCount(request.getPersonnelCount())
                .entranceCode(entranceCode)
                .template(request.getTemplate())
                .build();
        room = roomRepository.save(room);
        participantRepository.save(new Participant(loginMember.getNickname(), room));
        return uploadFileService.uploadAndSaveUploadFiles(uploadFiles, room);
    }
    // 방 입장 검증
    @Transactional
    public RoomEnterResponse enterRoom(String entranceCode, Member loginMember) {
        Room room = roomRepository.findByEntranceCode(entranceCode)
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다." + entranceCode));
        roomRepository.save(room);
        participantRepository.save(new Participant(loginMember.getNickname(), room));
        room.incrementParticipantCount();
        // 방의 현재 참가자 목록을 갱신
        List<String> nicknames = participantRepository.findByRoom(room).stream()
                .map(Participant::getNickname)
                .toList();

        // 웹소켓을 통해 참가자 수와 닉네임 목록을 실시간으로 방송
        messagingTemplate.convertAndSend("/sub/roomUpdate", new RoomUpdateNotification(room.getPersonnelCount(),nicknames, room.getHostNickname()));
        return new RoomEnterResponse(room.getEntranceCode());
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
                .toList();

        RoomUpdateNotification notification = new RoomUpdateNotification(
                room.getPersonnelCount(),
                remainingNicknames,
                room.getHostNickname()
        );
        broadcastRoomUpdate(notification);
        return notification;
    }

    private void broadcastRoomUpdate(RoomUpdateNotification notification) {
        messagingTemplate.convertAndSend("/sub/roomUpdate", notification);
    }

    public RoomInfoResponse getRoomInfoWithFiles(String entranceCode) {
        Room room = roomRepository.findByEntranceCode(entranceCode)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with entrance code: " + entranceCode));

        List<String> participantNicknames = participantRepository.findByRoom(room).stream()
                .map(Participant::getNickname)
                .toList();

        List<UploadFile> files = uploadFileRepository.findByRoomId(room.getId());

        List<RoomInfoResponse.FileDetail> pdfUrls = files.stream()
                .filter(f -> "application/pdf".equals(f.getFileType()))
                .map(f -> new RoomInfoResponse.FileDetail(f.getFileUrl(), f.getFileName()))
                .toList();

        List<String> codeUrls = files.stream()
                .filter(f -> f.getFileType().equals("text/x-python-script") || f.getFileType().equals("application/octet-stream"))
                .map(UploadFile::getFileUrl)
                .toList();

        return RoomInfoResponse.builder()
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .language(room.getLanguage())
                .personnelCount(room.getPersonnelCount())
                .entranceCode(room.getEntranceCode())
                .template(room.getTemplate())
                .hostNickname(room.getHostNickname())
                .participantNicknames(participantNicknames)
                .codeUrls(new RoomInfoResponse.CodeUrls(room.getLanguage(), codeUrls))
                .pdfUrls(pdfUrls)
                .build();
    }

    @Transactional
    public HostChangeResponse changeHost(String entranceCode, String currentHostNickname, String newHostNickname) {
        Room room = roomRepository.findByEntranceCode(entranceCode)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with id: " + entranceCode));

        if (!room.getHostNickname().equals(currentHostNickname)) {
            throw new IllegalArgumentException("Only the current host can change the host.");
        }

        Room updatedRoom = room.toBuilder()
                .hostNickname(newHostNickname)
                .build();
        roomRepository.save(updatedRoom);

        List<String> participantNicknames = participantRepository.findByRoom(updatedRoom).stream()
                .map(Participant::getNickname)
                .toList();

        RoomUpdateNotification notification = new RoomUpdateNotification(
                updatedRoom.getPersonnelCount(),
                participantNicknames,
                updatedRoom.getHostNickname()
        );
        broadcastRoomUpdate(notification);

        return HostChangeResponse.builder()
                .hostNickname(newHostNickname)
                .build();
    }




}





