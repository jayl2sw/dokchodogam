package com.ssafy.dockchodogam.dto.game;

import com.ssafy.dockchodogam.domain.enums.MonsterGrade;
import com.ssafy.dockchodogam.domain.enums.PlantType;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "MonsterRequestDto", description = "독초몬 신규 생성, 수정 Dto")
public class MonsterRequestDto {
    private String name;
    private int maxAttack;
    private int minAttack;
    private PlantType type;
    private MonsterGrade grade;
    private int hp;

    private String line; // 대사

    // image plant, user(최초 발견자)
}
