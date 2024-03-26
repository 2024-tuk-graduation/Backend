package com.example.tukgraduation.global.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;


@Getter
public class ResultResponse<T> extends RepresentationModel<ResultResponse<T>> {
    private final String code;
    private final int status;
    private final String message;
    private Object data;


    @JsonCreator
    public ResultResponse(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.status = resultCode.getStatus();
        this.message = resultCode.getMessage();
    }

    @JsonCreator
    public ResultResponse(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.status = resultCode.getStatus();
        this.message = resultCode.getMessage();
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
