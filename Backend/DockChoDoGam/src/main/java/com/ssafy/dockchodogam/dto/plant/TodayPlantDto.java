package com.ssafy.dockchodogam.dto.plant;

import com.ssafy.dockchodogam.domain.TodayPlant;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TodayPlantDto", description = "오늘의 식물 응답 Dto")
public class TodayPlantDto {
    private String img1;
    private String img2;
    private String img3;
    private String name;
    private String lang;
    private String content;
    private String useto;
    private String grow;
    private String type;

    public static TodayPlantDto of(TodayPlant entity){
        return TodayPlantDto.builder()
                .name(entity.getName())
                .img1(entity.getImg1())
                .img2(entity.getImg2())
                .img3(entity.getImg3())
                .lang(entity.getLang())
                .content(entity.getContent())
                .useto(entity.getUseto())
                .grow(entity.getGrow())
                .type(entity.getType())
                .build();
    }
}
