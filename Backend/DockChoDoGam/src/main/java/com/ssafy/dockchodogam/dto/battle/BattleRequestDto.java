package com.ssafy.dockchodogam.dto.battle;

import com.ssafy.dockchodogam.domain.Battle;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Data
@ApiModel(value = "BattleRequestDto", description = "배틀 생성 요청 Dto")
public class BattleRequestDto {
    private String attacker;
    private String defender;
    private boolean isRank;
    private Long monster0;
    private Long monster1;
    private Long monster2;
    private Long monster3;
    private Long monster4;
    private Long monster5;
    private Long monster6;
    private Long monster7;
    private Long monster8;
    private Long monster9;
    private boolean success;
    private boolean finish;
}
