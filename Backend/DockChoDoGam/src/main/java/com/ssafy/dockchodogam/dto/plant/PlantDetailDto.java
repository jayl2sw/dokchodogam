package com.ssafy.dockchodogam.dto.plant;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "PlantDetailDto", description = "플랜트 디테일 Dto")
public class PlantDetailDto {
    private Long plantId;
    private String name;
    private String engNm;
    private String familyKorNm;
    private String familyNm;
    private String genusKorNm;
    private String genusNm;
    private String plantSpecsScnm;
    private String imgUrl;
    private String shpe;
    private String spft;
    private String orplcNm;
    private String sz;
    private String smlrPlntDesc;
    private String flwrDesc;
    private String leafDesc;
    private String dstrb;
    private String stemDesc;
    private String fritDesc;
    private String branchDesc;
    private String woodDesc;
    private String sporeDesc;
    private String rootDesc;
    private String farmSpftDesc;
    private String grwEvrntDesc;
    private String useMthdDesc;
    private String cprtCtnt;
    private Long monsterId;
}
