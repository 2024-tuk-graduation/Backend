package com.example.tukgraduation.global.advice;

import com.example.tukgraduation.global.error.ErrorCode;

public class BadRequestException extends BusinessException {
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
