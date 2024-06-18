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
    private String mode;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ColorChange {
        private String color;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThicknessChange {
        private float thickness;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DrawTypeChange {
        private String drawType;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClearAll {
        private String clearAll;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErasePart {
        private double offsetX;
        private double offsetY;
        private boolean erasePart;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CodeMode {
        private boolean codeMode;
    
    }
}
