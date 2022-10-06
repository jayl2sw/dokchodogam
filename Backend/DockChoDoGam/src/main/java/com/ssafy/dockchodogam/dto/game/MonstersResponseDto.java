package com.ssafy.dockchodogam.dto.game;

import com.ssafy.dockchodogam.domain.Monster;
import com.ssafy.dockchodogam.domain.enums.MonsterGrade;
import com.ssafy.dockchodogam.domain.enums.PlantType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MonstersResponseDto", description = "독초몬 목록 응답 Dto")
public class MonstersResponseDto {
    @ApiModelProperty(name="몬스터 아이디", example="1")
    private Long monsterId;
    @ApiModelProperty(name="몬스터 이름", example="개나리몬")
    private String name;
    @ApiModelProperty(name="최고전투력", example="10")
    private int maxAttack;
    @ApiModelProperty(name="최소전투력", example="1")
    private int minAttack;
    @ApiModelProperty(name="몬스터 타입", example="[DOKCHO, YAKCHO, JAPCHO]")
    private PlantType type;
    @ApiModelProperty(name="몬스터 이미지", example="/img/monster.png")
    private String images;
    @ApiModelProperty(name="몬스터 등급", example="[COMMON, RARE, EPIC, LEGENDARY]")
    private MonsterGrade grade;
    @ApiModelProperty(name="몬스터 체력", example="1")
    private int hp;

    @ApiModelProperty(name="몬스터 현재 체력", example="1")
    private int currentHp;
    @ApiModelProperty(name="유저 보유 여부", example="true")
    private boolean isGot;

    @ApiModelProperty(name="대사", example="This is Me")
    private String line;
    @ApiModelProperty(name="식물 아이디", example="1")
    private Long plantId;

    public static MonstersResponseDto of(Monster monster, boolean isGot) {
        return MonstersResponseDto.builder()
                .monsterId(monster.getMonsterId())
                .name(monster.getName())
                .maxAttack(monster.getMaxAttack())
                .minAttack(monster.getMinAttack())
                .type(monster.getType())
                .images(monster.getImages())
                .grade(monster.getGrade())
                .hp(monster.getHp())
                .currentHp(monster.getHp())
                .isGot(isGot)
                .line(monster.getLine())
                .plantId(monster.getPlantId())
                .build();
    }
}
