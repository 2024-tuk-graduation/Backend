package com.example.tukgraduation.image.exception;

import com.example.tukgraduation.global.advice.BusinessException;
import com.example.tukgraduation.global.error.ErrorCode;

public class S3UploadException extends BusinessException {
    public S3UploadException() {
        super(ErrorCode.S3_UPLOAD_ERROR);
    }
}
