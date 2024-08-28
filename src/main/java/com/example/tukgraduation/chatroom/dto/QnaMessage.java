package com.example.tukgraduation.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QnaMessage {
    private String id; // unique identifier for each message
    private String type; // "question" or "answer"
    private String content;
    private Optional<String> parentId = Optional.empty(); // Optional field to store the ID of the parent message (used only for answers)
}