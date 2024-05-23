package com.example.tukgraduation.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostChangeRequest {
    private String entranceCode;
    private String currentHostNickname;
    private String newHostNickname;
}
