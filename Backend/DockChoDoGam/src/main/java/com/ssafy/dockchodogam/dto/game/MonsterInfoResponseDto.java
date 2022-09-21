package com.ssafy.dockchodogam.dto.game;

import com.ssafy.dockchodogam.domain.Monster;
import com.ssafy.dockchodogam.domain.enums.MonsterGrade;
import com.ssafy.dockchodogam.domain.enums.PlantType;
import com.ssafy.dockchodogam.dto.user.UserResponseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MonsterInfoResponseDto", description = "독초몬 상세 정보 응답 Dto")
public class MonsterInfoResponseDto {
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
    @ApiModelProperty(name="최초 발견자 아이디", example="user_name")
    private String firstFinder;

    @ApiModelProperty(name="대사", example="This is Me")
    private String line;

    public static MonsterInfoResponseDto of(Monster monster) {
        return MonsterInfoResponseDto.builder()
                .monsterId(monster.getMonsterId())
                .name(monster.getName())
                .maxAttack(monster.getMaxAttack())
                .minAttack(monster.getMinAttack())
                .type(monster.getType())
                .images(monster.getImages())
                .grade(monster.getGrade())
                .hp(monster.getHp())
                .firstFinder(monster.getFirstFinder())
                .line(monster.getLine())
                .build();
    }
}
