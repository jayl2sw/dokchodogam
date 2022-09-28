package com.ssafy.dockchodogam.service.battle;

import com.ssafy.dockchodogam.domain.Battle;
import com.ssafy.dockchodogam.domain.BattleLog;
import com.ssafy.dockchodogam.dto.gg.BattleDto;
import com.ssafy.dockchodogam.dto.gg.BattleLogDto;
import com.ssafy.dockchodogam.dto.gg.BattleLogRequestDto;
import com.ssafy.dockchodogam.dto.gg.BattleRequestDto;
import com.ssafy.dockchodogam.repository.BattleLogRepository;
import com.ssafy.dockchodogam.repository.BattleRepository;
import com.ssafy.dockchodogam.repository.MonsterRepository;
import com.ssafy.dockchodogam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Properties;

@Service
@RequiredArgsConstructor
@Transactional
public class BattleServiceImpl implements BattleService {

    private final MonsterRepository monsterRepository;
    private final UserRepository userRepository;
    private final BattleRepository battleRepository;
    private final BattleLogRepository battleLogRepository;

    public void createNewBattle(BattleRequestDto data) {
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
                .success(false)
                .build();

        battleRepository.save(battle);
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
                .isFinished(data.isFinished())
                .success(data.isSuccess())
                .build();

        battleLogRepository.save(battleLog);

        if (data.isFinished()) {

            Battle battle = battleRepository.findById(data.getBattleId()).get();
            BattleDto battleDto = new BattleDto().from(battle);
            if (data.isSuccess()) {
                battle.successBattle();
            }
            // 카프카로 배틀 보내는 로직 작성
            Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(ProducerConfig.CLIENT_ID_CONFIG, "DokchoMainServer");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

            KafkaProducer<String, Object> producer = new KafkaProducer<>(props);
            producer.send(new ProducerRecord<>("battles", battleDto), (recordMeta, exception) -> {
               if (exception == null) {
                   System.out.println("Record written to offset " +
                   recordMeta.offset() + "timestamp " + recordMeta.timestamp());
               } else {
                   System.err.println("An error occurred");
                   exception.printStackTrace(System.err);
               }
            });

            producer.flush();
            producer.close();
        }
    }
}