package com.virtualclassroom.authrestapi.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;
}
