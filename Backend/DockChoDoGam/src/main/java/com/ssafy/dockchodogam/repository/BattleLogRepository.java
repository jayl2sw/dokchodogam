package com.ssafy.dockchodogam.repository;


import com.ssafy.dockchodogam.domain.BattleLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BattleLogRepository  extends JpaRepository<BattleLog, Long> {

    List<BattleLog> findBattleLogsByBattleId(Long battleLogId);

}
