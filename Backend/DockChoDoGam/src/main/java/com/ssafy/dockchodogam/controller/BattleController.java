package com.ssafy.dockchodogam.controller;

import com.ssafy.dockchodogam.dto.battle.BattleLogRequestDto;
import com.ssafy.dockchodogam.dto.battle.BattleRequestDto;
import com.ssafy.dockchodogam.dto.battle.BattleStatusDto;
import com.ssafy.dockchodogam.service.battle.BattleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> createNewBattle(@RequestBody BattleRequestDto data) {
        Long newBattleId = battleService.createNewBattle(data);
        Map<String, Object> res = new HashMap<>();
        res.put("battle_id", newBattleId);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
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

    @PostMapping("/finish")
    @ApiOperation(value = "새 배틀 로그 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Void.class),
            @ApiResponse(code = 401, message = "Fail", response = Void.class)
    })
    public ResponseEntity<String> createNewBattleLog(@RequestBody BattleStatusDto data) throws ChangeSetPersister.NotFoundException {
        battleService.finishBattle(data);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

}
