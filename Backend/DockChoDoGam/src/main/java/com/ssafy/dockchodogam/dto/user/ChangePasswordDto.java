package com.ssafy.dockchodogam.dto.user;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "ChangePasswordDto", description = "비밀번호 변경 Dto")
public class ChangePasswordDto {
    private String newPW;
}
