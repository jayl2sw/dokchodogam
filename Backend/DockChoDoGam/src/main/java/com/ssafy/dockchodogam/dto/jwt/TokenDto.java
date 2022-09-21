package com.ssafy.dockchodogam.dto.jwt;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TokenDto", description = "JWT 토큰 양식")
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiresIn;
}
