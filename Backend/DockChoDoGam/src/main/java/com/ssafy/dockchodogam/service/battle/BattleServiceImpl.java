package com.ssafy.dockchodogam.service.battle;

import com.ssafy.dockchodogam.domain.Battle;
import com.ssafy.dockchodogam.domain.BattleLog;
import com.ssafy.dockchodogam.dto.battle.BattleDto;
import com.ssafy.dockchodogam.dto.battle.BattleLogRequestDto;
import com.ssafy.dockchodogam.dto.battle.BattleRequestDto;
import com.ssafy.dockchodogam.dto.battle.BattleStatusDto;
import com.ssafy.dockchodogam.repository.BattleLogRepository;
import com.ssafy.dockchodogam.repository.BattleRepository;
import com.ssafy.dockchodogam.repository.MonsterRepository;
import com.ssafy.dockchodogam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
                .success(false)
                .wellFinished(false)
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

        BattleDto battleDto = new BattleDto().from(battle);
//        battleDtoKafkaTemplate.send("battles", null, battleDto);

    }
}