package com.example.tukgraduation.chatroom.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DrawData {
    private double lastX;
    private double lastY;
    private double offsetX;
    private double offsetY;
    private boolean isDrawing;
}