package com.example.tukgraduation.member.dto;

import lombok.Getter;

@Getter
public class AuthInfo {

    private Long id;
    private String username;

    public AuthInfo(String username, Long id) {
        this.id = id;
        this.username = username;
    }
}