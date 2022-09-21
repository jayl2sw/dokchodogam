package com.ssafy.dockchodogam.dto.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@ApiModel(value = "UpdateUserRequsetDto", description = "유저정보 수정 요청 Dto")
public class UpdateUserRequestDto {
    private String password;
    private String nickname;
    private String email;
}
