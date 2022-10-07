package com.ssafy.dockchodogam.domain;

import com.ssafy.dockchodogam.dto.plant.PlantRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "plant")
public class Plant {

    @Id
    @Column(name = "plant_id")
    private Long plantId;
    @Column(name = "name")
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

    @ManyToOne
    @JoinColumn(name = "monster_id")
    private Monster monster;

    public void update(PlantRequestDto dto) {
        this.name = dto.getName();
        this.engNm = dto.getEngNm();
        this.familyKorNm = dto.getFamilyKorNm();
        this.familyNm = dto.getFamilyNm();
        this.genusKorNm = dto.getGenusKorNm();
        this.genusNm = dto.getGenusNm();
        this.plantSpecsScnm = dto.getPlantSpecsScnm();
        this.imgUrl = dto.getImgUrl();
        this.shpe = dto.getShpe();
        this.spft = dto.getSpft();
        this.orplcNm = dto.getOrplcNm();
        this.sz = dto.getSz();
        this.smlrPlntDesc = dto.getSmlrPlntDesc();
        this.flwrDesc = dto.getFlwrDesc();
        this.leafDesc = dto.getLeafDesc();
        this.dstrb = dto.getDstrb();
        this.stemDesc = dto.getStemDesc();
        this.fritDesc = dto.getFritDesc();
        this.branchDesc = dto.getBranchDesc();
        this.woodDesc = dto.getWoodDesc();
        this.sporeDesc = dto.getSporeDesc();
        this.rootDesc = dto.getRootDesc();
        this.farmSpftDesc = dto.getFarmSpftDesc();
        this.grwEvrntDesc = dto.getGrwEvrntDesc();
        this.useMthdDesc = dto.getUseMthdDesc();
        this.cprtCtnt = dto.getCprtCtnt();
    }
    public void update(String name, String engNm, String familyKorNm, String familyNm, String genusKorNm, String genusNm, String plantSpecsScnm, String imgUrl, String shpe, String spft, String orplcNm, String sz, String smlrPlntDesc, String flwrDesc, String leafDesc, String dstrb, String stemDesc, String fritDesc, String branchDesc, String woodDesc, String sporeDesc, String rootDesc, String farmSpftDesc, String grwEvrntDesc, String useMthdDesc, String cprtCtnt) {
        this.name = name;
        this.engNm = engNm;
        this.familyKorNm = familyKorNm;
        this.familyNm = familyNm;
        this.genusKorNm = genusKorNm;
        this.genusNm = genusNm;
        this.plantSpecsScnm = plantSpecsScnm;
        this.imgUrl = imgUrl;
        this.shpe = shpe;
        this.spft = spft;
        this.orplcNm = orplcNm;
        this.sz = sz;
        this.smlrPlntDesc = smlrPlntDesc;
        this.flwrDesc = flwrDesc;
        this.leafDesc = leafDesc;
        this.dstrb = dstrb;
        this.stemDesc = stemDesc;
        this.fritDesc = fritDesc;
        this.branchDesc = branchDesc;
        this.woodDesc = woodDesc;
        this.sporeDesc = sporeDesc;
        this.rootDesc = rootDesc;
        this.farmSpftDesc = farmSpftDesc;
        this.grwEvrntDesc = grwEvrntDesc;
        this.useMthdDesc = useMthdDesc;
        this.cprtCtnt = cprtCtnt;
    }
}
