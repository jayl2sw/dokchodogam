package com.ssafy.dockchodogam.controller;

import com.ssafy.dockchodogam.domain.Item;
import com.ssafy.dockchodogam.dto.game.DeckRequestDto;
import com.ssafy.dockchodogam.dto.game.MonsterInfoResponseDto;
import com.ssafy.dockchodogam.dto.game.MonstersResponseDto;
import com.ssafy.dockchodogam.dto.game.RankerProfileResponseDto;
import com.ssafy.dockchodogam.dto.user.FriendResponseDto;
import com.ssafy.dockchodogam.dto.user.UserResponseDto;
import com.ssafy.dockchodogam.service.game.GameService;
import com.ssafy.dockchodogam.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/game")
public class GameController {

    private final GameService gameService;
    private final UserService userService;

    @GetMapping("/monster/list")
    @ApiOperation(value = "독초몬 리스트 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = MonstersResponseDto.class)
    })
    public ResponseEntity<?> getMonsters(Pageable pageable){
        // 로그인 유저
        Long userId = userService.getMyInfo().getUser_id();
        Page<MonstersResponseDto> list = gameService.getMonsterList(userId, pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping("/monster/mylist")
    @ApiOperation(value = "유저가 가진 독초몬 리스트 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = MonsterInfoResponseDto.class)
    })
    public ResponseEntity<?> getMyMonsters(Pageable pageable){
        Long userId = userService.getMyInfo().getUser_id();
        Slice<MonsterInfoResponseDto> list = gameService.getMyMonsterList(userId, pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/monster/detail/{monster_id}")
    @ApiOperation(value = "특정 독초몬 상세 정보 조회", notes = "<strong>몬스터 아이디</strong>를 통해 특정 몬스터의 상세 정보를 조회한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = MonsterInfoResponseDto.class)
    })
    public ResponseEntity<?> getMonsterInfo(
            @PathVariable @ApiParam(value="몬스터 아이디", required = true) Long monster_id){
        // 특정 독초몬의 상세 정보 조회
        return new ResponseEntity<>(gameService.getMonsterInfo(monster_id), HttpStatus.OK);
    }

    @PutMapping("/deck/save")
    @ApiOperation(value = "덱 수정", notes = "수정할 덱 구성을 통해 덱을 수정하고," +
            "<br/>해당 덱이 새로 만들어진 것인지 수정한 것인지에 따라 false, true가 반환된다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Boolean.class)
    })
    public ResponseEntity<?> buildDeck(
            @RequestBody @ApiParam(value="수정 덱", required = true) DeckRequestDto deck){
        // 덱 수정
        Long userId = userService.getMyInfo().getUser_id();
        return new ResponseEntity<>(gameService.changeDeck(deck, userId), HttpStatus.OK);
    }

    @GetMapping("/deck/myInfo")
    @ApiOperation(value = "나의 덱 정보 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = MonsterInfoResponseDto.class)
    })
    public ResponseEntity<?> getMyDeck(){
        // 나의 덱 정보 조회 (독초몬 다섯 마리 + 상세 정보)
        Long userId = userService.getMyInfo().getUser_id();
        return new ResponseEntity<>(gameService.getMyDeck(userId), HttpStatus.OK);
    }

    @GetMapping("/deck/opponentInfo")
    @ApiOperation(value = "비슷한 랭킹의 상대 세 명의 프로필 정보와 덱 정보 조회",
            notes = "opponents 키로 상대 세 명의 UserResponseDto를, yourDecks 키로 상대 세 명의 MonsterInfoResponseDto의 리스트를 응답한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Map.class)
    })
    public ResponseEntity<?> getYourDeck(){
        // 상대 덱 정보 조회 (독초몬 다섯 마리 + 상세 정보)
        Map<String, Object> map = new HashMap<>();
        UserResponseDto user = userService.getMyInfo();
        List<UserResponseDto> opponents = gameService.getOpponents(user.getUser_id(), user.getRank_point(), 10);
        List<List<MonsterInfoResponseDto>> yourDecks = opponents
                .stream().map(s -> gameService.getMyDeck(s.getUser_id()))
                .collect(Collectors.toList());
        map.put("opponents", opponents);
        map.put("yourDecks", yourDecks);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @GetMapping("/deck/friendInfo/{user_id}")
    @ApiOperation(value = "친구의 덱 조회", notes = "친구의 아이디로 친구의 덱을 조회한다")
    public ResponseEntity<?> getFriendDeck(@PathVariable @ApiParam(value = "친구 아이디") Long user_id){
        return new ResponseEntity<>(gameService.getMyDeck(user_id), HttpStatus.OK);
    }

    
    // 아직 안 함 ======================================================================================================
    @PostMapping("/battle/turn")
    @ApiOperation(value = "배틀 결과 저장")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Void.class),
            @ApiResponse(code = 401, message = "Fail", response = Void.class)
    })
    public ResponseEntity<?> getBattleResult(
            @RequestBody @ApiParam(value = "배틀 기록 정보", required = true) Object result){
        // 로그 저장
        // 승자와 패자 점수 조정
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/ranking")
    @ApiOperation(value = "상위 랭커 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = RankerProfileResponseDto.class)
    })
    public ResponseEntity<?> getRanking(){
        // 상위 다섯 랭커 조회
        // 대표독초몬 닉네임 덱 점수
        return new ResponseEntity<>(gameService.getTop5(), HttpStatus.OK);
    }

    @GetMapping("/myranking")
    @ApiOperation(value = "나의 랭킹 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Long.class)
    })
    public ResponseEntity<?> getMyRanking(){
        // 내 랭킹 조회
        int rankPoint = userService.getMyInfo().getRank_point();
        return new ResponseEntity<>(gameService.getMyRank(rankPoint), HttpStatus.OK);
    }

    @GetMapping("/shop")
    @ApiOperation(value = "판매 상품 리스트 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Item.class)
    })
    public ResponseEntity<?> getPackages(){
        // 판매 상품 리스트 조회
        return new ResponseEntity<>(gameService.getItems(), HttpStatus.OK);
    }

    @GetMapping("/monster/pick/{item_id}")
    @ApiOperation(value = "독초몬 뽑기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = MonstersResponseDto.class)
    })
    public ResponseEntity<?> getRandomMonsters(
            @PathVariable @ApiParam(value = "아이템 아이디") Long item_id){
        UserResponseDto user = userService.getMyInfo();
        // 랜덤 독초몬 뽑기
        // 이미 있는 독초몬이라면 일부 냥 돌려주기
        // 새로운 독초몬이라면 유저 보유 독초몬에 추가
        return new ResponseEntity<>(gameService.getRandomMonsters(user, item_id), HttpStatus.OK);
    }

    @PostMapping("/charge")
    @ApiOperation(value = "캐시 충전")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = String.class)
    })
    public ResponseEntity<?> chargeCash(
            @RequestBody @ApiParam(value = "충전할 캐시") int cash){
        Long userId = userService.getMyInfo().getUser_id();
        // 유저 캐시 수정
        gameService.chargeCash(userId, cash);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/monster")
    @ApiOperation(value = "독초몬 추가")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = String.class)
    })
    public ResponseEntity<?> addMonster(
            @RequestBody @ApiParam(value = "추가할 몬스터 id") Long monsterId){
        Long userId = userService.getMyInfo().getUser_id();
        gameService.addMonster(userId, monsterId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}
