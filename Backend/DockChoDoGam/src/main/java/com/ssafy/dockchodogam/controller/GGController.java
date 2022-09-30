package com.ssafy.dockchodogam.controller;

import com.ssafy.dockchodogam.dto.battle.BattleDto;
import com.ssafy.dockchodogam.dto.gg.GGRequestDto;
import com.ssafy.dockchodogam.service.battle.BattleService;
import com.ssafy.dockchodogam.service.game.GameService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gg")
public class GGController {
    private final BattleService battleService;
    private final GameService gameService;

    @GetMapping("/log/user/{nickname}/{page}")
    @ApiOperation(value = "회원 전적 검색")
    public ResponseEntity<Map<String, Object>> searchLog(@PathVariable String nickname, @PathVariable int page){
        Map<String, Object> map = new HashMap<>();
        map.put("BattleDto", battleService.searchLog(nickname, page));
        map.put("winRate", battleService.getWinRate(nickname));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/log/moster/{monster_id}/{page}")
    @ApiOperation(value = "독초몬 별 통계")
    public ResponseEntity<Map<String, Object>> searchMonster(@PathVariable Long monster_id, @PathVariable int page){
        Map<String, Object> map = new HashMap<>();
        map.put("monsterDto", gameService.getMonsterInfo(monster_id));
        map.put("winRate", battleService.getWinRate(monster_id));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
