package com.ssafy.dockchodogam.service.battle;

import com.ssafy.dockchodogam.domain.Battle;
import com.ssafy.dockchodogam.domain.BattleLog;
import com.ssafy.dockchodogam.dto.battle.*;
import com.ssafy.dockchodogam.dto.exception.user.UserNotFoundException;
import com.ssafy.dockchodogam.dto.gg.PickRate;
import com.ssafy.dockchodogam.dto.gg.WinRate;
import com.ssafy.dockchodogam.repository.BattleLogRepository;
import com.ssafy.dockchodogam.repository.BattleRepository;
import com.ssafy.dockchodogam.repository.MonsterRepository;
import com.ssafy.dockchodogam.repository.UserRepository;
import com.ssafy.dockchodogam.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class BattleServiceImpl implements BattleService {

    private final MonsterRepository monsterRepository;
    private final UserRepository userRepository;
    private final BattleRepository battleRepository;
    private final BattleLogRepository battleLogRepository;
    private final KafkaTemplate<String, BattleDto> battleDtoKafkaTemplate;
    private final KafkaTemplate<String, BattleLogDto> battleLogDtoKafkaTemplate;


    public Long createNewBattle(BattleRequestDto data) {
        Battle battle = Battle.builder()
                .attacker(userRepository.findByNickname(data.getAttacker()).get())
                .defender(userRepository.findByNickname(data.getDefender()).get())
                .monster0(monsterRepository.findMonsterByMonsterId(data.getMonster0()))
                .monster1(monsterRepository.findMonsterByMonsterId(data.getMonster1()))
                .monster2(monsterRepository.findMonsterByMonsterId(data.getMonster2()))
                .monster3(monsterRepository.findMonsterByMonsterId(data.getMonster3()))
                .monster4(monsterRepository.findMonsterByMonsterId(data.getMonster4()))
                .monster5(monsterRepository.findMonsterByMonsterId(data.getMonster5()))
                .monster6(monsterRepository.findMonsterByMonsterId(data.getMonster6()))
                .monster7(monsterRepository.findMonsterByMonsterId(data.getMonster7()))
                .monster8(monsterRepository.findMonsterByMonsterId(data.getMonster8()))
                .monster9(monsterRepository.findMonsterByMonsterId(data.getMonster9()))
                .isRank(data.isRank())
                .success(data.isSuccess())
                .wellFinished(data.isFinish())
                .build();

        Battle saved = battleRepository.save(battle);
        return saved.getBattle_id();
    }

    @Override
    public void createNewBattleLog(BattleLogRequestDto data) {
        BattleLog battleLog = BattleLog.builder()
                .battleId(data.getBattleId())
                .attackMonsterId(data.getAttackMonsterId())
                .attackMonsterDamage(data.getAttackMonsterDamage())
                .attackMonsterHp(data.getAttackMonsterHp())
                .defendMonsterId(data.getDefendMonsterId())
                .defendMonsterDamage(data.getDefendMonsterDamage())
                .defendMonsterHp(data.getDefendMonsterHp())
                .round(data.getRound())
                .skill(data.getSkill())
                .skillUsage(data.isSkillUsage())
                .build();


        battleLogRepository.save(battleLog);
        battleLogDtoKafkaTemplate.send("battleLog", BattleLogDto.from(battleLog));

    }

    @Override
    public void finishBattle(BattleStatusDto data) throws ChangeSetPersister.NotFoundException {
        Battle battle = battleRepository.findById(data.getBattle_id()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if (data.isSuccess()) {
            battle.successBattle();
        }
        battle.finishBattle();

        BattleDto battleDto = BattleDto.from(battle);

        battleDtoKafkaTemplate.send("battles", String.valueOf(battleDto.getBattle_id()), battleDto);

    }

    @Override
    public List<BattleDto> searchLog(int page){
        Long myId = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new).getUserId();
        return battleRepository.findBattlesByUser(myId, PageRequest.of(page, 20)).stream().map(battle -> BattleDto.from(battle)).collect(Collectors.toList());
    }

    @Override
    public List<BattleDto> searchLog(String nickname, int page){
        Long myId = userRepository.findByNickname(nickname).orElseThrow(UserNotFoundException::new).getUserId();
        return battleRepository.findBattlesByUser(myId, PageRequest.of(page, 20)).stream().map(battle -> BattleDto.from(battle)).collect(Collectors.toList());
    }

    @Override
    public List<BattleDto> searchLog(Long monsterId, int page){
        Long myId = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new).getUserId();
        return battleRepository.findBattlesByUserAndMonster(PageRequest.of(page, 20), myId, monsterId).stream().map(battle -> BattleDto.from(battle)).collect(Collectors.toList());
    }

    @Override
    public WinRate getWinRate(){
        Long myId = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new).getUserId();
        return battleRepository.findWinRateByUser(myId);
    }

    @Override
    public WinRate getWinRate(String nickname){
        Long myId = userRepository.findByNickname(nickname).orElseThrow(UserNotFoundException::new).getUserId();
        return battleRepository.findWinRateByUser(myId);
    }

    @Override
    public WinRate getTotalWinRate(Long monsterId){
        return battleRepository.findWinRateByMonster(monsterId);
    }

    @Override
    public WinRate getWinRate(Long monsterId){
        Long myId = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new).getUserId();

        return battleRepository.findWinRateByMonsterAndUser(monsterId, myId);
    }

    @Override
    public List<PickRate> getPickRate() {
        Long userId = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new).getUserId();

        return battleRepository.findPickRateByUser(userId);
    }

    @Override
    public List<PickRate> getPickRate(String nickname) {
        Long userId = userRepository.findByNickname(nickname).orElseThrow(UserNotFoundException::new).getUserId();

        return battleRepository.findPickRateByUser(userId);
    }

    @Override
    public List<WinRate> getMonsterRanking() {
        return battleRepository.findWinRateRanking();
    }
}