package com.example.tukgraduation.chatroom.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PRIVATE)
@Getter
public class CompilerResponse {

    @NotNull
    private String output;
}

