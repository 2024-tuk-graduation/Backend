package com.example.tukgraduation.chatroom.repository;

import com.example.tukgraduation.chatroom.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByEntranceCode(String entranceCode);

}
