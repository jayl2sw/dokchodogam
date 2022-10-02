package com.ssafy.dockchodogam.repository;

import com.ssafy.dockchodogam.domain.Battle;
import com.ssafy.dockchodogam.domain.User;
import com.ssafy.dockchodogam.dto.gg.PickRate;
import com.ssafy.dockchodogam.dto.gg.WinRate;
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

    @Query(nativeQuery = true, value = "select * from battle" +
            " where (user_id1 = :user and" +
            " (monster_id0 = :monster or monster_id1 = :monster or monster_id2 = :monster or monster_id3 = :monster or monster_id4 = :monster))" +
            " or (user_id2 = :user and" +
            " (monster_id5 = :monster or monster_id6 = :monster or monster_id7 = :monster or monster_id8 = :monster or monster_id9 = :monster))" +
            " order by created_date desc")
    Page<Battle> findBattlesByUserAndMonster(Pageable pageable, @Param("user") Long user, @Param("monster") Long monster);

    @Query(nativeQuery = true, value = "select count(*) from battle where user_id1 = :user")
    Integer findAttackCountByUser(@Param("user") Long user);

    @Query(nativeQuery = true, value = "select count(*) from battle where user_id1 = :user and success = true")
    Integer findAttackSuccessCountByUser(@Param("user") Long user);

    @Query(nativeQuery = true, value = "select count(*) from battle where user_id2 = :user")
    Integer findDefenceCountByUser(@Param("user") Long user);

    @Query(nativeQuery = true, value = "select count(*) from battle where user_id2 = :user and success = false")
    Integer findDefenceSuccessCountByUser(@Param("user") Long user);

    @Query(nativeQuery = true, value = "select count(*) from battle where monster_id0 = :monster" +
            " or monster_id1 = :monster or monster_id2 = :monster or monster_id3 = :monster or monster_id4 = :monster")
    Integer findAttackCountByMonster(@Param("monster") Long monster);

    @Query(nativeQuery = true, value = "select count(*) from battle where (monster_id0 = :monster" +
            " or monster_id1 = :monster or monster_id2 = :monster or monster_id3 = :monster or monster_id4 = :monster) and success = true")
    Integer findAttackSuccessCountByMonster(@Param("monster") Long monster);

    @Query(nativeQuery = true, value = "select count(*) from battle where monster_id5 = :monster" +
            " or monster_id6 = :monster or monster_id7 = :monster or monster_id8 = :monster or monster_id9 = :monster")
    Integer findDefenceCountByMonster(@Param("monster") Long monster);

    @Query(nativeQuery = true, value = "select count(*) from battle where (monster_id5 = :monster" +
            " or monster_id6 = :monster or monster_id7 = :monster or monster_id8 = :monster or monster_id9 = :monster) and success = false")
    Integer findDefenceSuccessCountByMonster(@Param("monster") Long monster);

    @Query(nativeQuery = true, value = "select count(*) from battle where user_id1 = :user and (monster_id0 = :monster" +
            " or monster_id1 = :monster or monster_id2 = :monster or monster_id3 = :monster or monster_id4 = :monster)")
    Integer findAttackCountByMonsterAndUser(@Param("monster") Long monster, @Param("user") Long user);

    @Query(nativeQuery = true, value = "select count(*) from battle where user_id1 = :user and (monster_id0 = :monster" +
            " or monster_id1 = :monster or monster_id2 = :monster or monster_id3 = :monster or monster_id4 = :monster) and success = true")
    Integer findAttackSuccessCountByMonsterAndUser(@Param("monster") Long monster, @Param("user") Long user);

    @Query(nativeQuery = true, value = "select count(*) from battle where user_id2 = :user and (monster_id5 = :monster" +
            " or monster_id6 = :monster or monster_id7 = :monster or monster_id8 = :monster or monster_id9 = :monster)")
    Integer findDefenceCountByMonsterAndUser(@Param("monster") Long monster, @Param("user") Long user);

    @Query(nativeQuery = true, value = "select count(*) from battle where user_id2 = :user and (monster_id5 = :monster" +
            " or monster_id6 = :monster or monster_id7 = :monster or monster_id8 = :monster or monster_id9 = :monster) and success = false")
    Integer findDefenceSuccessCountByMonsterAndUser(@Param("monster") Long monster, @Param("user") Long user);

    @Query(nativeQuery = true, value = "select m.monster_id as monsterId, count(*) as pickCount, (select count(*) from battle where user_id1 = :user) as totalGame, count(*) / (select count(*) from battle where user_id1 = :user) as pickRate " +
            "from battle b join monster m " +
            "on b.monster_id0 = m.monster_id or b.monster_id1 = m.monster_id or b.monster_id2 = m.monster_id or b.monster_id3 = m.monster_id or b.monster_id4 = m.monster_id " +
            "where b.user_id1 = :user " +
            "group by m.monster_id " +
            "order by pickRate desc;")
    List<PickRate> findPickRate(@Param("user") Long user);

    @Query(nativeQuery = true, value = "select m.monster_id as monsterId, count(*) as winGame, (select count(*) from battle " +
                        "where monster_id0 = m.monster_id or monster_id1 = m.monster_id or monster_id2 = m.monster_id or monster_id3 = m.monster_id or monster_id4 = m.monster_id or  "+
                        "monster_id5 = m.monster_id or monster_id6= m.monster_id or monster_id7 = m.monster_id or monster_id8 = m.monster_id or monster_id9 = m.monster_id) as totalGame, count(*) / (select count(*) from battle " +
            "where monster_id0 = m.monster_id or monster_id1 = m.monster_id or monster_id2 = m.monster_id or monster_id3 = m.monster_id or monster_id4 = m.monster_id or " +
            "monster_id5 = m.monster_id or monster_id6= m.monster_id or monster_id7 = m.monster_id or monster_id8 = m.monster_id or monster_id9 = m.monster_id) as winRate " +
            "from battle b join monster m " +
            "on ((b.monster_id0 = m.monster_id or b.monster_id1 = m.monster_id or b.monster_id2 = m.monster_id or b.monster_id3 = m.monster_id or b.monster_id4 = m.monster_id) and b.success = true) " +
            "or ((b.monster_id5 = m.monster_id or b.monster_id6 = m.monster_id or b.monster_id7 = m.monster_id or b.monster_id8 = m.monster_id or b.monster_id9 = m.monster_id) and b.success = false) " +
            "group by m.monster_id " +
            "order by winRate desc;")
    List<WinRate> findWinRate();
}
