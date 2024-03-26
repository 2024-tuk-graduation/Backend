package com.example.tukgraduation.member.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
public class AuthInfo {

    private String username;

    public AuthInfo(String username) {
        this.username = username;
    }
}