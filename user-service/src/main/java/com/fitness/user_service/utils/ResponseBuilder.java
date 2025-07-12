package com.fitness.user_service.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseBuilder<T> {

    private String erc;
    private T data;
    private String msg;

    public static <T> ResponseBuilder<?> dataReturn(T data) {
        return ResponseBuilder.builder()
                .erc(Constants.SUCCESS)
                .data(data)
                .build();
    }

    public static <T> ResponseBuilder<?> errorMsgReturn(String message) {
        return ResponseBuilder.builder()
                .erc(Constants.FAILURE)
                .msg(message)
                .build();
    }
    public static <T> ResponseBuilder<?> successMsgReturn(String message) {
        return ResponseBuilder.builder()
                .erc(Constants.FAILURE)
                .msg(message)
                .build();
    }
}
