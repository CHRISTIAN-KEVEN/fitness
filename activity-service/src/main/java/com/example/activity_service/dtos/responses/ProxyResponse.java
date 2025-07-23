package com.example.activity_service.dtos.responses;

import lombok.Data;

@Data
public class ProxyResponse <T> {

    private String erc;
    private T data;
    private String msg;
}
