package com.ssafy.dockchodogam.controller;

import com.ssafy.dockchodogam.domain.Plant;
import com.ssafy.dockchodogam.dto.plant.PlantDetailDto;
import com.ssafy.dockchodogam.dto.plant.PlantListDto;
import com.ssafy.dockchodogam.dto.plant.TodayPlantDto;
import com.ssafy.dockchodogam.service.dokcho.DokchoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dokcho")
public class DokchoController {

    private final DokchoService dokchoService;

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test() {
        Map<String, Object> res = new HashMap<>();
        res.put("data", "test");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping("/list/{page}")
    @ApiOperation(value = "식물 리스트 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Void.class),
            @ApiResponse(code = 401, message = "Fail", response = Void.class)
    })
    public ResponseEntity<Map<String, Object>> getPlants(int page){
        // 모든 식물 리스트 조회
        List<PlantListDto> plants = dokchoService.findAllPlants(page);
        Map<String, Object> res = new HashMap<>();
        res.put("data", plants);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/detail/{dokcho_id}")
    @ApiOperation(value = "식물 상세 정보 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Void.class),
            @ApiResponse(code = 401, message = "Fail", response = Void.class)
    })
    public ResponseEntity<Map<String, Object>> getPlantInfo(
            @PathVariable @ApiParam(value="독초 아이디", required = true) Long dokcho_id){
        // 특정 식물의 상세 정보 조회
        PlantDetailDto plantInfo = dokchoService.findPlantDetail(dokcho_id);

        Map<String, Object> res = new HashMap<>();
        res.put("data", plantInfo);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}/{page}/{size}")
    @ApiOperation(value = "식물 검색")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Void.class),
            @ApiResponse(code = 401, message = "Fail", response = Void.class)
    })
    public ResponseEntity<Map<String, Object>> searchPlantInfo(
            @PathVariable @ApiParam(value="검색 키워드", required = true) String keyword,
            @PathVariable @ApiParam(value="검색 페이지", required = true) int page,
            @PathVariable @ApiParam(value="페이지당 레코드 수", required = true) int size){

        // 해당 키워드를 포함한 식물 리스트 조회
        List<PlantListDto> plantList = dokchoService.searchPlants(keyword, page, size);
        // 식물 검색 로그 저장해도 좋을 듯


        Map<String, Object> res = new HashMap<>();
        res.put("data", plantList);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/judge")
    @ApiOperation(value = "식물 판별")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Void.class),
            @ApiResponse(code = 401, message = "Fail", response = Void.class)
    })
    public ResponseEntity<Map<String, Object>> judgePlant(
            @RequestPart @ApiParam(value="검색할 식물 사진", required = true) MultipartFile file) throws Exception {
        // plant.id api를 통해 식물 판별
        // 토큰으로 로그인 유저 판별 후 독초몬 수집
        // 누가 언제 어디에서 어떤 식물을 찍었는지 저장
        String imgurl = dokchoService.savePlantImage(file);
        Map<String, Object> plantData = dokchoService.judgeImage(imgurl);

        Map<String, Object> res = new HashMap<>();
        if (!(boolean) plantData.get("plantExist")){
            res.put("plant", null);
            res.put("errCode", plantData.get("errCode"));
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        Plant plant = (Plant) plantData.get("plant");
        PlantDetailDto plantDto = dokchoService.createDto(plant);
        res.put("plant", plantDto);

        boolean onDogam = false;
        boolean isOverlapped = false;
        boolean isDokcho = false;

        if (plant.getMonster()!= null) {
            onDogam = true;
            isOverlapped = dokchoService.checkUserDogam(plant.getMonster().getMonsterId());
            if (!isOverlapped) {
                dokchoService.addFoundMonster(plant.getMonster());
            }

            if (plant.getMonster().getType().toString() == "DOKCHO") {
                isDokcho = true;
            }
        }
        res.put("onDogam", onDogam);
        res.put("isOverlapped", isOverlapped);
        res.put("isDokcho", isDokcho);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/today")
    @ApiOperation(value = "오늘의 식물")
    public ResponseEntity<TodayPlantDto> getTodayPlant(){

        return new ResponseEntity<TodayPlantDto>(dokchoService.getTodayPlant(), HttpStatus.OK);
    }
}
