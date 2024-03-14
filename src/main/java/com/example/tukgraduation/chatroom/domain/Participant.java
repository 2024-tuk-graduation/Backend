package com.example.tukgraduation.chatroom.domain;

import com.example.tukgraduation.global.config.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Participant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname; // 참가자의 닉네임

    @ManyToOne
    private Room room; // 참가한 방에 대한 참조

    public Participant(String nickname, Room room) {
        this.nickname = nickname;
        this.room = room;
    }
}
