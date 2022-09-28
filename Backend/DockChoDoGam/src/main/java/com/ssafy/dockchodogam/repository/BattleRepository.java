package com.ssafy.dockchodogam.repository;

import com.ssafy.dockchodogam.domain.Battle;
import com.ssafy.dockchodogam.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BattleRepository extends JpaRepository<Battle, Long> {

    List<Battle> findBattlesByAttacker(User user);
    List<Battle> findBattlesByDefender(User user);

    @Query(value = "select * from Battle where attacker = :user or defender = :user limit :limit offset :offset order by createdDate", nativeQuery = true )
    List<Battle> findBattlesByUser(@Param("user") User user, @Param("offset") int offset, @Param("limit") int limit);
}
