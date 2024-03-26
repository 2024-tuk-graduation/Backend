package com.example.tukgraduation.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {

    //Member
    UPLOAD_SUCCESS("A001", 200, "이미지 업로드 성공"),
    USER_NOT_LOGIN("L001",201,"로그인이 필요합니다."),
    USER_LOGIN_SUCCESS("L002",200, "로그인 성공"),
    SIGN_UP_SUCCESS("S001", 200, "회원가입 성공"),


    ;

    private final String code;
    private final int status;
    private final String message;
}
