package com.ssafy.dockchodogam.dto.gg;

import lombok.*;


@Data
public class BattleLogRequestDto {
    private Integer round;
    private Long battleId;
    private Long attackMonsterId;
    private Long defendMonsterId;
    private Integer attackMonsterHp;
    private Integer defendMonsterHp;
    private Integer attackMonsterDamage;
    private Integer defendMonsterDamage;
    private Long skill;
    private boolean skillUsage;
    private boolean finished;
    private boolean success;
}
