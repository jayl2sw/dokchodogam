package com.ssafy.dockchodogam.repository;

import com.ssafy.dockchodogam.domain.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, Long> {

    // 모든 식물 리스트 찾기
    Page<Plant> findAll(Pageable pageable);

    // 기본 이름을로 찾기
    Optional<Plant> findByName(String name);

    // 학명으로 식물 찾기
    Optional<Plant> findByplantSpecsScnm(String plantSpecsScnm);

    // genus로 식물 찾기
    Optional<Plant> findPlantByGenusNm(String genusNm);

    // species로 식물찾기
    Optional<Plant> findPlantByEngNm(String engNm);

    // 이름으로 식물 검색
    @Query("select p from Plant p where p.name like %:keyword%")
    Page<Plant> searchPlantsByKeyword(@Param("keyword") String keyword, Pageable pageable);


}
