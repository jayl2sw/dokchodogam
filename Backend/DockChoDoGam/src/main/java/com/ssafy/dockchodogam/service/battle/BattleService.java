package com.ssafy.dockchodogam.service.battle;

import com.ssafy.dockchodogam.dto.battle.BattleDto;
import com.ssafy.dockchodogam.dto.battle.BattleLogRequestDto;
import com.ssafy.dockchodogam.dto.battle.BattleRequestDto;
import com.ssafy.dockchodogam.dto.battle.BattleStatusDto;
import com.ssafy.dockchodogam.dto.gg.PickRate;
import com.ssafy.dockchodogam.dto.gg.WinRate;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface BattleService {

    Long createNewBattle(BattleRequestDto data);
    void createNewBattleLog(BattleLogRequestDto data);
    void finishBattle(BattleStatusDto data) throws ChangeSetPersister.NotFoundException;
    List<BattleDto> searchLog(int page);
    List<BattleDto> searchLog(String nickname, int page);
    List<BattleDto> searchLog(Long monsterId, int page);
    WinRate getWinRate();
    WinRate getWinRate(String nickname);
    WinRate getTotalWinRate(Long monsterId);
    WinRate getWinRate(Long monsterId);
    List<PickRate> getPickRate();
    List<PickRate> getPickRate(String nickname);
    List<WinRate> getMonsterRanking();
}
