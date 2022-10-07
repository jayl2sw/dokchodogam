package com.ssafy.dockchodogam.domain;

import com.ssafy.dockchodogam.domain.basetime.BaseTime;
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
@Table(name = "Archive")
public class Archive extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "archive_id")
    private Long archiveId;

    @Column(name = "imgURL")
    private String imgURL;
    private String userNickname;
    private boolean isPlant;
    private double probability;
    private String suggestionPlantNm;
    private String suggestionImg;

    public Archive(String imgURL, String userNickname, boolean isPlant, double probability, String suggestionPlantNm, String suggestionImg) {
        this.imgURL = imgURL;
        this.userNickname = userNickname;
        this.isPlant = isPlant;
        this.probability = probability;
        this.suggestionPlantNm = suggestionPlantNm;
        this.suggestionImg = suggestionImg;
    }
}
