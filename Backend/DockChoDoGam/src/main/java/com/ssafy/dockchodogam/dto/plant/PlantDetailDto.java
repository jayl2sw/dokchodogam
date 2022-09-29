package com.ssafy.dockchodogam.dto.plant;

import com.ssafy.dockchodogam.domain.Monster;
import com.ssafy.dockchodogam.domain.Plant;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.Locale;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public static PlantDetailDto from(Plant plant) {
        Long monster_id = plant.getMonster() == null ? null : plant.getMonster().getMonsterId();
        return PlantDetailDto.builder()
                .branchDesc(plant.getBranchDesc())
                .cprtCtnt(plant.getCprtCtnt())
                .dstrb(plant.getDstrb())
                .engNm(plant.getEngNm())
                .familyKorNm(plant.getFamilyKorNm())
                .familyNm(plant.getFamilyNm())
                .farmSpftDesc(plant.getFarmSpftDesc())
                .flwrDesc(plant.getFlwrDesc())
                .fritDesc(plant.getFritDesc())
                .genusKorNm(plant.getGenusKorNm())
                .genusNm(plant.getGenusNm())
                .grwEvrntDesc(plant.getGrwEvrntDesc())
                .imgUrl(plant.getImgUrl())
                .leafDesc(plant.getLeafDesc())
                .monsterId(monster_id)
                .name(plant.getName())
                .orplcNm(plant.getOrplcNm())
                .plantId(plant.getPlantId())
                .plantSpecsScnm(plant.getPlantSpecsScnm())
                .rootDesc(plant.getRootDesc())
                .shpe(plant.getShpe())
                .spft(plant.getSpft())
                .sporeDesc(plant.getSporeDesc())
                .sz(plant.getSz())
                .smlrPlntDesc(plant.getSmlrPlntDesc())
                .stemDesc(plant.getStemDesc())
                .useMthdDesc(plant.getUseMthdDesc())
                .woodDesc(plant.getWoodDesc())
                .build();
    }
}
