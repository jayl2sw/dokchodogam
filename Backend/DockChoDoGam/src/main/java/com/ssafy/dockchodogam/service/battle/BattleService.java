package com.ssafy.dockchodogam.service.battle;

import com.ssafy.dockchodogam.dto.battle.BattleDto;
import com.ssafy.dockchodogam.dto.battle.BattleLogRequestDto;
import com.ssafy.dockchodogam.dto.battle.BattleRequestDto;
import com.ssafy.dockchodogam.dto.battle.BattleStatusDto;
import com.ssafy.dockchodogam.dto.gg.GGRequestDto;
import com.ssafy.dockchodogam.dto.gg.WinRateDto;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface BattleService {

    Long createNewBattle(BattleRequestDto data);
    void createNewBattleLog(BattleLogRequestDto data);
    void finishBattle(BattleStatusDto data) throws ChangeSetPersister.NotFoundException;
    List<BattleDto> searchLog(String nickname, int page);
    WinRateDto getWinRate(String nickname);
}
