package com.example.tukgraduation.chatroom.dto;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class DrawData {
    private double lastX;
    private double lastY;
    private double offsetX;
    private double offsetY;
    private boolean isDrawing;


    @NoArgsConstructor
    @AllArgsConstructor
    public static class ColorChange {
        private String color;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThicknessChange {
        private float thickness;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    public static class DrawTypeChange {
        private String drawType;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClearAll {
        private boolean clearAll;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErasePart {
        private double offsetX;
        private double offsetY;
        private boolean erasePart;
    }
}