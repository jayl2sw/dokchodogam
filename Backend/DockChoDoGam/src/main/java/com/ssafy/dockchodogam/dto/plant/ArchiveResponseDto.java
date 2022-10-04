package com.ssafy.dockchodogam.dto.plant;

import com.ssafy.dockchodogam.domain.Archive;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
@ApiModel(value = "ArchiveResponseDto", description = "아카이브 응답 Dto")
public class ArchiveResponseDto {
    private Long archiveId;
    private String imgURL;
    private String userNickname;
    private String picturedTime;
    private boolean isPlant;
    private double probability;
    private String suggestionPlantNm;
    private String suggestionImg;


    public static ArchiveResponseDto from(Archive archive) {
        String createdDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(archive.getCreatedDate()).toString();

        return ArchiveResponseDto.builder()
                .archiveId(archive.getArchiveId())
                .imgURL(archive.getImgURL())
                .userNickname(archive.getUserNickname())
                .isPlant(archive.isPlant())
                .probability(archive.getProbability())
                .suggestionImg(archive.getSuggestionImg())
                .suggestionPlantNm(archive.getSuggestionPlantNm())
                .picturedTime(createdDate)
                .build();
    }
}
