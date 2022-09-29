package com.ssafy.dockchodogam.dto.battle;

import com.ssafy.dockchodogam.domain.Battle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BattleDto {
    private long battle_id;
    private String attacker;
    private String defender;
    private boolean success;
    private boolean isRank;
    private String battle_time;
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

    public static BattleDto from(Battle battle) {
        String battle_time = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(battle.getCreatedDate()).toString();

        return BattleDto.builder()
                .battle_id(battle.getBattle_id())
                .attacker(battle.getAttacker().getNickname())
                .defender(battle.getDefender().getNickname())
                .isRank(battle.isRank())
                .success(battle.isSuccess())
                .battle_time(battle_time)
                .monster0(battle.getMonster0().getMonsterId())
                .monster1(battle.getMonster1().getMonsterId())
                .monster2(battle.getMonster2().getMonsterId())
                .monster3(battle.getMonster3().getMonsterId())
                .monster4(battle.getMonster4().getMonsterId())
                .monster5(battle.getMonster5().getMonsterId())
                .monster6(battle.getMonster6().getMonsterId())
                .monster7(battle.getMonster7().getMonsterId())
                .monster8(battle.getMonster8().getMonsterId())
                .monster9(battle.getMonster9().getMonsterId())
                .build();
    }
}
