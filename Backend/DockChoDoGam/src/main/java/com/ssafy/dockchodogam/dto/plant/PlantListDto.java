package com.ssafy.dockchodogam.dto.plant;

import com.ssafy.dockchodogam.domain.enums.PlantType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "PlantListDto", description = "플랜트 리스트 Dto")
public class PlantListDto {
    private Long plantId;
    private String name;
    private String engNm;
    private String imgUrl;
    private String familyKorNm;
    private String genusKorNm;
    private String PlantSpecsScnm;
}
