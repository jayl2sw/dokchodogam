package com.ssafy.dockchodogam.service.battle;

import com.ssafy.dockchodogam.dto.gg.BattleDto;
import com.ssafy.dockchodogam.dto.gg.BattleLogDto;
import com.ssafy.dockchodogam.dto.gg.BattleLogRequestDto;
import com.ssafy.dockchodogam.dto.gg.BattleRequestDto;

public interface BattleService {

    void createNewBattle(BattleRequestDto data);
    void createNewBattleLog(BattleLogRequestDto data);
}
