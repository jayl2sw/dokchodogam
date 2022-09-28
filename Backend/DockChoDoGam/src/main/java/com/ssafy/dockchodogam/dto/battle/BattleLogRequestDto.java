package com.ssafy.dockchodogam.dto.battle;

import io.swagger.annotations.ApiModel;
import lombok.*;


@Data
@ApiModel(value = "BattleLogRequestDto", description = "배틀 로그 생성 요청 Dto")
public class BattleLogRequestDto {
    private Integer round;
    private Long battleId;
    private Long attackMonsterId;
    private Long defendMonsterId;
    private Integer attackMonsterHp;
    private Integer defendMonsterHp;
    private Integer attackMonsterDamage;
    private Integer defendMonsterDamage;
    private String skill;
    private boolean skillUsage;
    private boolean isFinished;
    private boolean success;
}
