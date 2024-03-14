package com.example.tukgraduation.chatroom.domain;

import com.example.tukgraduation.global.config.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Room extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entranceCode; // 입장 코드
    private String hostNickname; // 호스트의 닉네임
    private String language; // 사용 언어
    private String roomName; // 방 이름
    private Long roomCount; // 방 인원 수
    private int participantCount = 0; // 참가자 수

    // 입장 인원 증가 메서드
    public void incrementParticipantCount() {
        this.participantCount++;
    }

    public void decrementParticipantCount() {
        this.participantCount--;
    }

    public void setHostNickname(String hostNickname) {
        this.hostNickname = hostNickname;
    }

}

