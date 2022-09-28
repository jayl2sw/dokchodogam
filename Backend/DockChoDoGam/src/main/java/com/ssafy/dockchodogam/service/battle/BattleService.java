package com.ssafy.dockchodogam.service.battle;

import com.ssafy.dockchodogam.dto.battle.BattleLogRequestDto;
import com.ssafy.dockchodogam.dto.battle.BattleRequestDto;

public interface BattleService {

    Long createNewBattle(BattleRequestDto data);
    void createNewBattleLog(BattleLogRequestDto data);
}
