package com.example.tukgraduation.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Global
    INTERNAL_SERVER_ERROR( "G001", 500, "예상치 못한 서버 내부 오류"),
    EXTERNAL_LIBRARY_ERROR("GOO2", 400, "외부 라이브러리로 인해 예외가 발생했습니다."),
    // Auth
    AUTHORIZED_ERROR("A001", 400, "접근 권한이 없습니다. 로그인이 유효한지 확인해주세요"),
    LOGIN_FAILED_ERROR("A002", 400, "아이디나 비밀번호가 잘못되었습니다"),
    DUPLICATE_NICKNAME_ERROR("M001", 400, "이미 존재하는 닉네임입니다."),
    S3_UPLOAD_ERROR("I001", 400, "S3 이미지 업로드가 실패하였습니다"),


    ;
    private final String code;
    private final int status;
    private final String message;

    @Override
    public String toString() {
        return this.code + " : " + this.message;
    }
}
