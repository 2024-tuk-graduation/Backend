package com.example.tukgraduation.global.result;

import lombok.Getter;

@Getter
public class ResultResponse {
    private final int status; //http status code
    private final String message;
    private final Object data;

    public ResultResponse(ResultCode result, Object data) {
        this.status = result.getStatus();
        this.message = result.getMessage();
        this.data = data;
    }

    //응답할 data가 따로 없음
    public static ResultResponse of(ResultCode result) {
        return new ResultResponse(result, "");
    }

    //응답할 데이터가 있음
    public static ResultResponse of(ResultCode result, Object data) {
        return new ResultResponse(result, data);
    }
}
