package com.example.tukgraduation.chatroom.domain;

import com.example.tukgraduation.global.config.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName; // 방 이름
    private String entranceCode; // 입장 코드
    private String hostNickname; // 호스트의 닉네임
    private String language; // 사용 언어
    private int personnelCount; // 방 인원 수
    private int participantCount = 1; // 참가자 수
    private int template;


    @ElementCollection
    private List<String> uploadFiles;

    // 입장 인원 증가 메서드
    public void incrementParticipantCount() {
        this.participantCount++;
    }

    public void decrementParticipantCount() {
        this.participantCount--;
    }
}

