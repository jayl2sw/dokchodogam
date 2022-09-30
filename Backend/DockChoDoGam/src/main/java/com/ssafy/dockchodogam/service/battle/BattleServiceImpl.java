package com.ssafy.dockchodogam.service.battle;

import com.ssafy.dockchodogam.domain.Battle;
import com.ssafy.dockchodogam.domain.BattleLog;
import com.ssafy.dockchodogam.domain.Monster;
import com.ssafy.dockchodogam.domain.User;
import com.ssafy.dockchodogam.dto.battle.BattleDto;
import com.ssafy.dockchodogam.dto.battle.BattleLogRequestDto;
import com.ssafy.dockchodogam.dto.battle.BattleRequestDto;
import com.ssafy.dockchodogam.dto.battle.BattleStatusDto;
import com.ssafy.dockchodogam.dto.exception.user.UserNotFoundException;
import com.ssafy.dockchodogam.dto.gg.GGRequestDto;
import com.ssafy.dockchodogam.dto.gg.WinRateDto;
import com.ssafy.dockchodogam.repository.BattleLogRepository;
import com.ssafy.dockchodogam.repository.BattleRepository;
import com.ssafy.dockchodogam.repository.MonsterRepository;
import com.ssafy.dockchodogam.repository.UserRepository;
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

        System.out.println(battle);
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

        System.out.println(true);
        battleLogRepository.save(battleLog);
    }

    @Override
    public void finishBattle(BattleStatusDto data) throws ChangeSetPersister.NotFoundException {
        Battle battle = battleRepository.findById(data.getBattle_id()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if (data.isSuccess()) {
            battle.successBattle();
        }
        battle.finishBattle();

        BattleDto battleDto = BattleDto.from(battle);

        battleDtoKafkaTemplate.send("battles", null, battleDto);

    }

    @Override
    public List<BattleDto> searchLog(String nickname, int page){
        Long userId = userRepository.findByNickname(nickname).orElseThrow(UserNotFoundException::new).getUserId();
        return battleRepository.findBattlesByUser(userId, PageRequest.of(page, 20)).stream().map(battle -> BattleDto.from(battle)).collect(Collectors.toList());
    }

    @Override
    public WinRateDto getWinRate(String nickname){
        Long userId = userRepository.findByNickname(nickname).orElseThrow(UserNotFoundException::new).getUserId();
        int attackCnt = battleRepository.findAttackCountByUser(userId);
        int attackSuccess = battleRepository.findAttackSuccessCountByUser(userId);
        int defenceCnt = battleRepository.findDefenceCountByUser(userId);
        int defenceSuccess = battleRepository.findDefenceSuccessCountByUser(userId);
        int totalCnt = attackCnt + defenceCnt;
        double winRate = (double) (attackSuccess + defenceSuccess) / (double) totalCnt;

        return WinRateDto.builder()
                .totalGames(totalCnt)
                .attackCnt(attackCnt)
                .winAttack(attackSuccess)
                .defenceCnt(defenceCnt)
                .winDefence(defenceSuccess)
                .winRate(winRate)
                .build();
    }

    @Override
    public WinRateDto getWinRate(Long monsterId){
        int attackCnt = battleRepository.findAttackCountByMonster(monsterId);
        int attackSuccess = battleRepository.findAttackSuccessCountByMonster(monsterId);
        int defenceCnt = battleRepository.findDefenceCountByMonster(monsterId);
        int defenceSuccess = battleRepository.findDefenceSuccessCountByMonster(monsterId);
        int totalCnt = attackCnt + defenceCnt;
        double winRate = (double) (attackSuccess + defenceSuccess) / (double) totalCnt;

        return WinRateDto.builder()
                .totalGames(totalCnt)
                .attackCnt(attackCnt)
                .winAttack(attackSuccess)
                .defenceCnt(defenceCnt)
                .winDefence(defenceSuccess)
                .winRate(winRate)
                .build();
    }
}