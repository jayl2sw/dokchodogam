package com.ssafy.dockchodogam.dto.gg;

import com.ssafy.dockchodogam.domain.BattleLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private boolean isFinished;
    private boolean success;
}
