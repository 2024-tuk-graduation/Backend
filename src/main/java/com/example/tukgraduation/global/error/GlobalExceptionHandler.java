package com.example.tukgraduation.global.error;

import com.example.tukgraduation.global.advice.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Order(0)
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = new ErrorResponse(errorCode);

//        [ Error Level ]
//        trace < debug < info < warn < error
//        현재 logging level 은 info 로 설정되어, info, warn, error 만 로그에 남는다

//        log.trace(e.toString());
//        log.debug(e.toString());
//        log.info(e.toString());
//        log.warn(e.toString());
        log.error(e.toString());

        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

//    @Order(1)
//    @ExceptionHandler
//    protected ResponseEntity<String> handleException(Exception e) {
//
//        log.error(ErrorCode.INTERNAL_SERVER_ERROR + ", " + Arrays.toString(e.getStackTrace()));
//
//        return ResponseEntity.status(500).body("서버 내부 예외적 에러 발생");
//    }
}
