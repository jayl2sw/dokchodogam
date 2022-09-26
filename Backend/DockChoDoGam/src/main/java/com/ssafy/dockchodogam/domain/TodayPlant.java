package com.ssafy.dockchodogam.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "today_plant")
public class TodayPlant {
    @Id
    @Column(name = "today_plant_id")
    private Long todayPlantId;
    private Integer month;
    private Integer day;
    private String lang;
    private String img1;
    private String img2;
    private String img3;
    private String name;
    private String content;
    private String useto;
    private String grow;
    private String type;
}
