package com.example.tukgraduation.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QnaMessage {
    private String type; // "question" or "answer"
    private String content;
}