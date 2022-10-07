package com.ssafy.dockchodogam.dto.game;

import com.ssafy.dockchodogam.domain.Deck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DeckRequestDto", description = "덱 수정 요청 Dto")
public class DeckRequestDto {
    @ApiModelProperty(name="몬스터 아이디", example="1")
    private Long monster1;
    @ApiModelProperty(name="몬스터 아이디", example="2")
    private Long monster2;
    @ApiModelProperty(name="몬스터 아이디", example="3")
    private Long monster3;
    @ApiModelProperty(name="몬스터 아이디", example="4")
    private Long monster4;
    @ApiModelProperty(name="몬스터 아이디", example="5")
    private Long monster5;

    public static DeckRequestDto of(Deck deck) {
        return DeckRequestDto.builder()
                .monster1(deck.getMonster1().getMonsterId())
                .monster2(deck.getMonster2().getMonsterId())
                .monster3(deck.getMonster3().getMonsterId())
                .monster4(deck.getMonster4().getMonsterId())
                .monster5(deck.getMonster5().getMonsterId())
                .build();
    }
}
