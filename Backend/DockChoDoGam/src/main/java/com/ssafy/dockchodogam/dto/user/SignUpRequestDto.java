package com.ssafy.dockchodogam.dto.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@ApiModel(value = "SignUpRequestDto", description = "회원가입 요청 Dto")
public class SignUpRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String nickname;
    @NotBlank
    private String email;
}
