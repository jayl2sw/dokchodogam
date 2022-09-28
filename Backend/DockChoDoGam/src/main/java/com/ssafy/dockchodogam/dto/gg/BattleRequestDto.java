package com.ssafy.dockchodogam.dto.gg;

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
public class BattleRequestDto {
    private String attacker;
    private String defender;
    private boolean success;
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

}
