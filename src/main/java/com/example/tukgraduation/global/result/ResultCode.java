package com.example.tukgraduation.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {

    //Member
    USER_NOT_LOGIN(201,"로그인이 필요합니다."),
    USER_LOGIN_SUCCESS(200, "로그인 성공"),

    ;


    private final int status;
    private final String message;
}
