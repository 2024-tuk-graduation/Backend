package com.example.tukgraduation.chatroom.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PRIVATE)
@Getter
public class CompilerRequest {
    @NotEmpty
    private String language;

    @NotEmpty
    private String version;

    @NotEmpty
    private String code;

    private String input;
}
