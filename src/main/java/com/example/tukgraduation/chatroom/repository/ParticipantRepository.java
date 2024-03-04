package com.example.tukgraduation.chatroom.repository;

import com.example.tukgraduation.chatroom.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
