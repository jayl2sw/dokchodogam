package com.ssafy.dockchodogam.dto.gg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WinRateDto {
    private Integer totalGames;
    private Integer attackCnt;
    private Integer winAttack;
    private Integer defenceCnt;
    private Integer winDefence;
    private Double winRate;
}
