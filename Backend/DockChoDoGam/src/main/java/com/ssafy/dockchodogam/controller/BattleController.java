package com.ssafy.dockchodogam.controller;

import com.ssafy.dockchodogam.dto.gg.BattleDto;
import com.ssafy.dockchodogam.dto.gg.BattleLogDto;
import com.ssafy.dockchodogam.dto.gg.BattleLogRequestDto;
import com.ssafy.dockchodogam.dto.gg.BattleRequestDto;
import com.ssafy.dockchodogam.service.battle.BattleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/battle")
public class BattleController {

    private final BattleService battleService;

    @PostMapping("/new")
    @ApiOperation(value = "새 배틀 기록 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Void.class),
            @ApiResponse(code = 401, message = "Fail", response = Void.class)
    })
    public ResponseEntity<String> createNewBattle(@RequestBody BattleRequestDto data) {
        battleService.createNewBattle(data);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @PostMapping("/logs/new")
    @ApiOperation(value = "새 배틀 로그 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Void.class),
            @ApiResponse(code = 401, message = "Fail", response = Void.class)
    })
    public ResponseEntity<String> createNewBattleLog(@RequestBody BattleLogRequestDto data) {
        battleService.createNewBattleLog(data);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

}
