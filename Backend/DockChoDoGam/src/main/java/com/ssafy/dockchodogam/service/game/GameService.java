package com.ssafy.dockchodogam.service.game;


import com.ssafy.dockchodogam.domain.Item;
import com.ssafy.dockchodogam.domain.Plant;
import com.ssafy.dockchodogam.dto.game.DeckRequestDto;
import com.ssafy.dockchodogam.dto.game.MonsterInfoResponseDto;
import com.ssafy.dockchodogam.dto.game.MonstersResponseDto;
import com.ssafy.dockchodogam.dto.game.RankerProfileResponseDto;
import com.ssafy.dockchodogam.dto.user.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameService {

    // 모든 독초몬 리스트 조회
    Page<MonstersResponseDto> getMonsterList(Long userId, Pageable pageable);
    // 로그인 유저가 가진 독초몬 리스트 조회

    Page<MonsterInfoResponseDto> getMyMonsterList(Long userId, Pageable pageable);
    // 몬스터 아이디를 통해 특정 독초몬 상세 정보 조회
    MonsterInfoResponseDto getMonsterInfo(Long monsterId);
    // 로그인 유저의 덱 수정
    boolean changeDeck(DeckRequestDto monsters, Long userId);
    // 로그인 유저의 덱 정보 조회
    List<MonsterInfoResponseDto> getMyDeck(Long userId);
    // 로그인 유저의 배틀 상대 조회
    List<UserResponseDto> getOpponents(Long userId, int rankPoint, int range);
    // 특정 유저 점수 조정
    // 로그인 유저 랭킹 조회
    Long getMyRank(int rankPoint);
    // 배틀 상위 5 유저 조회
    List<RankerProfileResponseDto> getTop5();
    // 모든 랭킹 조회
    Page<RankerProfileResponseDto> getRanker(Pageable pageable);
    // 모든 판매 상품 리스트 조회
    List<Item> getItems();
    // 랜덤하게 독초몬 N개 조회
    List<MonstersResponseDto> getRandomMonsters(UserResponseDto user, Long itemId);
    // 로그인 유저 캐시 수정
    void chargeCash(Long userId, int cash);
    // 로그인 유저 몬스터 추가
    void addMonster(Long userId, Long monsterId);
    // 최초 발견자 업데이트
    void setFirstFinder(Plant plant);
    // 로그인 유저 보유 독초몬 수정
    // 로그인 유저 게임 정보 조회

}
