package com.ssafy.dockchodogam.service.game;

import com.ssafy.dockchodogam.domain.*;
import com.ssafy.dockchodogam.dto.exception.game.CashBelowZeroException;
import com.ssafy.dockchodogam.dto.exception.game.ItemNotFoundException;
import com.ssafy.dockchodogam.dto.exception.game.MonsterNotFoundException;
import com.ssafy.dockchodogam.dto.exception.game.SkillNotFoundException;
import com.ssafy.dockchodogam.dto.exception.user.UserNotFoundException;
import com.ssafy.dockchodogam.dto.game.DeckRequestDto;
import com.ssafy.dockchodogam.dto.game.MonsterInfoResponseDto;
import com.ssafy.dockchodogam.dto.game.MonstersResponseDto;
import com.ssafy.dockchodogam.dto.game.RankerProfileResponseDto;
import com.ssafy.dockchodogam.dto.user.UserResponseDto;
import com.ssafy.dockchodogam.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GameServiceImpl implements GameService {

    private final MonsterRepository monsterRepository;
    private final UserMonsterRepository userMonsterRepository;
    private final DeckRepository deckRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final PurchasesRepository purchasesRepository;

    @Override
    public Page<MonstersResponseDto> getMonsterList(Long userId, Pageable pageable) {
        return monsterRepository.findAll(pageable)
                .map(s->MonstersResponseDto.of(s,
                        userMonsterRepository.findUserMonsterByMonsterMonsterIdAndUserUserId(s.getMonsterId(), userId).isPresent()
                ));
    }

    @Override
    public Page<MonsterInfoResponseDto> getMyMonsterList(Long userId, Pageable pageable) {
        return userMonsterRepository.findUserMonsterByUserUserId(userId, pageable).map(s -> MonsterInfoResponseDto.of(s.getMonster()));
    }

    @Override
    public MonsterInfoResponseDto getMonsterInfo(Long monsterId) {
        Monster monster = monsterRepository.findById(monsterId).orElseThrow(MonsterNotFoundException::new);
        return MonsterInfoResponseDto.of(monster);
    }

    @Override
    public boolean changeDeck(DeckRequestDto monsters, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Optional<Deck> deck = deckRepository.findDeckByUserUserId(userId);
        if(deck.isPresent()){
            List<Monster> list = new ArrayList<>();
            list.add(monsterRepository.findMonsterByMonsterId(monsters.getMonster1()));
            list.add(monsterRepository.findMonsterByMonsterId(monsters.getMonster2()));
            list.add(monsterRepository.findMonsterByMonsterId(monsters.getMonster3()));
            list.add(monsterRepository.findMonsterByMonsterId(monsters.getMonster4()));
            list.add(monsterRepository.findMonsterByMonsterId(monsters.getMonster5()));

            deck.get().update(list);
            deckRepository.save(deck.get());
        }

        return true;
    }

    @Override
    public List<MonsterInfoResponseDto> getMyDeck(Long userId) {
        List<MonsterInfoResponseDto> list = new ArrayList<>();
        Optional<Deck> deck = deckRepository.findDeckByUserUserId(userId);
        list.add(MonsterInfoResponseDto.of(deck.get().getMonster1()));
        list.add(MonsterInfoResponseDto.of(deck.get().getMonster2()));
        list.add(MonsterInfoResponseDto.of(deck.get().getMonster3()));
        list.add(MonsterInfoResponseDto.of(deck.get().getMonster4()));
        list.add(MonsterInfoResponseDto.of(deck.get().getMonster5()));
        return list;
    }

    @Override
    public List<UserResponseDto> getOpponents(Long userId, int rankPoint, int range) {
        // 유저와 랭크 포인트가 비슷한 세 명 랜덤 고르기
        List<User> list = userRepository.findUsersByRank_pointBetween(userId, rankPoint - range, rankPoint + range);
        if (list.size() < 3) {
            // -10 ~ 10 사이에 3명이 없다면 가장 가까운 세 명 고르기
            list = userRepository.findUsersOrderByRank_Point(userId, rankPoint);
        }
        return list.stream().map(s -> UserResponseDto.from(s)).collect(Collectors.toList());
    }

    @Override
    public Long getMyRank(int rankPoint) {
        return userRepository.countUsersByRankPointGreaterThan(rankPoint) + 1;
    }

    @Override
    public List<RankerProfileResponseDto> getTop5() {
        return userRepository.findRanker()
                .stream().map(s -> RankerProfileResponseDto.of(s)).collect(Collectors.toList());
    }

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<MonstersResponseDto> getRandomMonsters(UserResponseDto userDto, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        // 아이템 캐시 가격만큼 유저 캐시 조정 (캐시 충전 시 -, 사용 시 +)
        int rest = userDto.getMoney() - item.getPriceCash();
        if (rest < 0) throw new CashBelowZeroException();
        User user = userRepository.findById(userDto.getUser_id()).orElseThrow(UserNotFoundException::new);
        user.changeMoney(rest);
        if(itemId == 2){ // 스타터 팩
            user.buyStarter();
        }
        userRepository.save(user);
        purchasesRepository.save(Purchases.builder().user(user).item(item).build());
        // 개수만큼 독초몬 랜덤하게 뽑기
        List<MonstersResponseDto> list = new ArrayList<>();
        List<Monster> monsters = monsterRepository.findRandomMonster(item.getItemCnt());
        for (int i = 0; i < item.getItemCnt(); i++) {
            Monster monster;
            // 중복 허용 (1개라면 중복 허용으로 넣기)
            if (item.isDuplicate()) {
                monster = monsterRepository.findRandomMonster();
            // 중복 비허용
            } else {
                monster = monsters.get(i);
            }
            boolean isGot = userMonsterRepository.findUserMonsterByMonsterMonsterIdAndUserUserId(monster.getMonsterId(), user.getUserId()) != null;
            if (isGot) {
                // 일부 환불 (10%)
                rest += item.getPriceCash() / (item.getItemCnt() * 10);
            } else {
                // userMonster에 삽입
                UserMonster userMonster = UserMonster.builder().user(user).monster(monster).build();
                userMonsterRepository.save(userMonster);
            }
            list.add(MonstersResponseDto.of(monster, isGot));
        }
        user.changeMoney(rest);
        return list;
    }

    @Override
    public void chargeCash(Long userId, int cash) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.changeMoney(user.getMoney() + cash);
        userRepository.save(user);
    }

    @Override
    public void addMonster(Long userId, Long monsterId) {
        if(userMonsterRepository.findUserMonsterByMonsterMonsterIdAndUserUserId(monsterId, userId).isPresent()){
            throw new RuntimeException("이미 보유 중");
        }

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Monster monster = monsterRepository.findById(monsterId).orElseThrow(MonsterNotFoundException::new);
        userMonsterRepository.save(UserMonster.builder().user(user).monster(monster).build());
    }

}
