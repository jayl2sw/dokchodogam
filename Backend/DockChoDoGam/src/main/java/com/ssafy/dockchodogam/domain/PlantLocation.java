package com.ssafy.dockchodogam.domain;

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
@Table(name = "plant_location")
public class PlantLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_location_id")
    private Long plantLocationId;
    @ManyToOne(targetEntity = Plant.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id")
    private Plant plant;
    @ManyToOne(targetEntity = Location.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

}
