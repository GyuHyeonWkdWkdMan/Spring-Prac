package me.gyuhyeon.blog.dto.req;

import lombok.*;

@Getter
@Setter
public class CreateAccessTokenRequest {
    private String refreshToken;
}
