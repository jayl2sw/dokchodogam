package com.ssafy.dockchodogam.dto.game;

import com.ssafy.dockchodogam.domain.Deck;
import com.ssafy.dockchodogam.domain.Monster;
import com.ssafy.dockchodogam.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "RankerProfileResponseDto", description = "상위 5위 랭커 프로필 응답 Dto")
public class RankerProfileResponseDto {
    @ApiModelProperty(name="랭커 아이디", example="1")
    private Long userId;
    @ApiModelProperty(name="랭커 닉네임", example="nickname")
    private String nickname;
    @ApiModelProperty(name="대표 독초몬 이미지", example="img/monster.png")
    private String monsterImages;
    @ApiModelProperty(name="랭커 점수", example="1")
    private int rankPoint;
    @ApiModelProperty(name="랭커 덱 몬스터 이미지 리스트")
    private String[] deck;

    public static RankerProfileResponseDto of(User user) {
        Deck deck = user.getDeck();
        return RankerProfileResponseDto.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .monsterImages(user.getRepresentMonster().getImages())
                .rankPoint(user.getRankPoint())
                .deck(new String[] {deck.getMonster1().getImages(), deck.getMonster2().getImages(),
                        deck.getMonster3().getImages(), deck.getMonster4().getImages(), deck.getMonster5().getImages()})
                .build();
    }
}
