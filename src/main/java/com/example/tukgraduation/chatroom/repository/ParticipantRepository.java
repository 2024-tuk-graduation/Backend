package com.example.tukgraduation.chatroom.repository;

import com.example.tukgraduation.chatroom.domain.Participant;
import com.example.tukgraduation.chatroom.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByRoom(Room room);
    Optional<Participant> findByRoomAndNickname(Room room, String nickname);

}
