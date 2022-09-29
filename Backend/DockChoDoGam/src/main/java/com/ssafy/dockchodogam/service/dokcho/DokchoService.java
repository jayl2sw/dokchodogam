package com.ssafy.dockchodogam.service.dokcho;

import com.ssafy.dockchodogam.domain.Monster;
import com.ssafy.dockchodogam.domain.Plant;
import com.ssafy.dockchodogam.dto.plant.PlantDetailDto;
import com.ssafy.dockchodogam.dto.plant.PlantListDto;
import com.ssafy.dockchodogam.dto.plant.TodayPlantDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface DokchoService {

    // 모든 식물 리스트 조회
    List<PlantListDto> findAllPlants(int page);

    // 독초 아이디를 통해 특정 식물 상세 정보 조회
    PlantDetailDto findPlantDetail(Long plantId);

    // 키워드를 통해 이를 포함하는 식물 리스트 조회
    List<PlantListDto> searchPlants(String keyword, Integer page, Integer size);

    // 유저 보유 독초몬 수정(추가)
    void addFoundMonster(Monster monster);
    
    // 학명으로 plant 검색
    PlantDetailDto findPlantByPlantSpecsScnm(String plantSpecsScnm);

    // 유저가 올린 이미지 서버에 저장
    String savePlantImage(MultipartFile file);

    // 이미지 경로를 통해 이미지 판별
    Map<String, Object> judgeImage(String path) throws Exception;

    boolean checkUserDogam(Long monsterId);

    TodayPlantDto getTodayPlant();
}
