package com.ssafy.dockchodogam.service.battle;

import com.ssafy.dockchodogam.dto.battle.BattleLogRequestDto;
import com.ssafy.dockchodogam.dto.battle.BattleRequestDto;
import com.ssafy.dockchodogam.dto.battle.BattleStatusDto;
import org.springframework.data.crossstore.ChangeSetPersister;

public interface BattleService {

    Long createNewBattle(BattleRequestDto data);
    void createNewBattleLog(BattleLogRequestDto data);
    void finishBattle(BattleStatusDto data) throws ChangeSetPersister.NotFoundException;
}
