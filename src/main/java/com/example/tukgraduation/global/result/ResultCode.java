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

    //Room
    ROOM_CREATE_SUCCESS("R001", 200, "방 생성 성공"),
    ROOM_ENTER_SUCCESS("ROO2", 200, "방 입장 성공"),
    ROOM_INFO_SUCCESS("ROO3", 200, "방 정보 조회 성공"),
    HOST_CHANGE_SUCCESS("R004", 200, "호스트 변경 성공"),

    //Compile
    COMPILE_SUCCESS("C001", 200, "컴파일 성공"),
    ;


    private final String code;
    private final int status;
    private final String message;
}
