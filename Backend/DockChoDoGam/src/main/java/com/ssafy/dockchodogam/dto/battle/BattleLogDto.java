package com.ssafy.dockchodogam.dto.battle;

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
public class BattleLogDto {
    private Long battleLogId;
    private Integer round;
    private Long battleId;
    private Long attackMonsterId;
    private Long defendMonsterId;
    private Integer attackMonsterHp;
    private Integer defendMonsterHp;
    private Integer attackMonsterDamage;
    private Integer defendMonsterDamage;
    private String createdDate;
    private Long skill;
    private boolean skillUsage;
    private boolean isFinished;
    private boolean success;

    public static BattleLogDto from(BattleLog battleLog) {
        String createdDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(battleLog.getCreatedDate()).toString();

        return BattleLogDto.builder()
                .battleLogId(battleLog.getBattleLogId())
                .round(battleLog.getRound())
                .attackMonsterId(battleLog.getAttackMonsterId())
                .defendMonsterId(battleLog.getDefendMonsterId())
                .attackMonsterHp(battleLog.getAttackMonsterHp())
                .attackMonsterDamage(battleLog.getAttackMonsterDamage())
                .defendMonsterHp(battleLog.getDefendMonsterHp())
                .defendMonsterDamage(battleLog.getDefendMonsterDamage())
                .createdDate(createdDate)
                .skill(battleLog.getSkill())
                .skillUsage(battleLog.isSkillUsage())
                .build();
    }
}
