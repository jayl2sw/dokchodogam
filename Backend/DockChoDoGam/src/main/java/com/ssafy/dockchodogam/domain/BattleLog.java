package com.ssafy.dockchodogam.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "battle_log")
public class BattleLog {

    @Id
    @GeneratedValue
    @Column(name="battle_log_id")
    private Long battleLogId;

    private Integer round;
    private Long battleId;
    private Long attackMonsterId;
    private Long defendMonsterId;
    private Integer attackMonsterHp;
    private Integer defendMonsterHp;
    private Integer attackMonsterDamage;
    private Integer defendMonsterDamage;
    private Long skillId;
    private boolean skillUsage;
}
