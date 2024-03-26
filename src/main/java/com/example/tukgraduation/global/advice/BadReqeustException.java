package com.example.tukgraduation.global.advice;

import com.example.tukgraduation.global.error.ErrorCode;

public class BadReqeustException extends BusinessException {
    public BadReqeustException(ErrorCode errorCode) {
        super(errorCode);
    }
}
