package com.ssafy.dockchodogam.repository;

import com.ssafy.dockchodogam.domain.Battle;
import com.ssafy.dockchodogam.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BattleRepository extends JpaRepository<Battle, Long> {

    List<Battle> findBattlesByAttacker(User user);
    List<Battle> findBattlesByDefender(User user);

    @Query(value = "select * from battle where user_id1 = :user or user_id2 = :user order by created_date desc", nativeQuery = true)
    Page<Battle> findBattlesByUser(@Param("user") Long user, Pageable pageable);

    @Query(nativeQuery = true, value = "select count(*) from battle where user_id1 = :user")
    Integer findAttackCountByUser(@Param("user") Long user);

    @Query(nativeQuery = true, value = "select count(*) from battle where user_id1 = :user and success = true")
    Integer findAttackSuccessCountByUser(@Param("user") Long user);

    @Query(nativeQuery = true, value = "select count(*) from battle where user_id2 = :user")
    Integer findDefenceCountByUser(@Param("user") Long user);

    @Query(nativeQuery = true, value = "select count(*) from battle where user_id2 = :user and success = false")
    Integer findDefenceSuccessCountByUser(@Param("user") Long user);
}
