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

    @Query(nativeQuery = true, value = "select m.monster_id as monsterId, m.name as monsterName, count(*) as pickCount, (select count(*) from battle where user_id1 = :user) as totalGame, count(*) * 100 / (select count(*) from battle where user_id1 = :user) as pickRate " +
            "from battle b join monster m " +
            "on b.monster_id0 = m.monster_id or b.monster_id1 = m.monster_id or b.monster_id2 = m.monster_id or b.monster_id3 = m.monster_id or b.monster_id4 = m.monster_id " +
            "where b.user_id1 = :user " +
            "group by m.monster_id " +
            "order by pickRate desc;")
    List<PickRate> findPickRateByUser(@Param("user") Long user);

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
    List<WinRate> findWinRateRanking();

    @Query(nativeQuery = true, value = "select u.user_id as userId, count(*) as winGame, (select count(*) from battle where user_id1 = :user or user_id2 = :user) as totalGame, count(*) / (select count(*) from battle where user_id1 = :user or user_id2 = :user) as winRate " +
            "from battle b join users u " +
            "on (b.user_id1 = u.user_id and b.success = true) or (b.user_id2 = u.user_id and success = false) " +
            "where u.user_id = :user ;")
    WinRate findWinRateByUser(@Param("user") Long user);

    @Query(nativeQuery = true, value = "select :monster as monsterId, count(*) as winGame, (select count(*) from battle " +
            "where monster_id0 = :monster or monster_id1 = :monster or monster_id2 = :monster or monster_id3 = :monster or monster_id4 = :monster or monster_id5 = :monster or " +
            "monster_id6 = :monster or monster_id7 = :monster or monster_id8 = :monster or monster_id9 = :monster) as totalGame, count(*) / (select count(*) from battle " +
            "where monster_id0 = :monster or monster_id1 = :monster or monster_id2 = :monster or monster_id3 = :monster or monster_id4 = :monster or monster_id5 = :monster or " +
            "monster_id6 = :monster or monster_id7 = :monster or monster_id8 = :monster or monster_id9 = :monster) as winRate " +
            "from battle " +
            "where ((monster_id0 = :monster or monster_id1 = :monster or monster_id2 = :monster or monster_id3 = :monster or monster_id4 = :monster) and success = true) " +
            "or ((monster_id5 = :monster or monster_id6 = :monster or monster_id7 = :monster or monster_id8 = :monster or monster_id9 = :monster) and success = false);")
    WinRate findWinRateByMonster(@Param("monster")Long monster);

    @Query(nativeQuery = true, value = "select :user as userId, :monster as monsterId, count(*) as winGame, (select count(*) from battle " +
            "where (((monster_id0 = :monster or monster_id1 = :monster or monster_id2 = :monster or monster_id3 = :monster or monster_id4 = :monster) and user_id1 = :user) " +
            " or ((monster_id5 = :monster or monster_id6 = :monster or monster_id7 = :monster or monster_id8 = :monster or monster_id9 = :monster) and user_id2 = :user))) as totalGame, count(*) / (select count(*) from battle " +
            "where (((monster_id0 = :monster or monster_id1 = :monster or monster_id2 = :monster or monster_id3 = :monster or monster_id4 = :monster) and user_id1 = :user) " +
            " or ((monster_id5 = :monster or monster_id6 = :monster or monster_id7 = :monster or monster_id8 = :monster or monster_id9 = :monster) and user_id2 = :user))) as winRate " +
            "from battle " +
            "where ((monster_id0 = :monster or monster_id1 = :monster or monster_id2 = :monster or monster_id3 = :monster or monster_id4 = :monster) and success = true and user_id1 = :user) " +
            "or ((monster_id5 = :monster or monster_id6 = :monster or monster_id7 = :monster or monster_id8 = :monster or monster_id9 = :monster) and success = false and user_id2 = :user);")
    WinRate findWinRateByMonsterAndUser(@Param("monster")Long monster, @Param("user")Long user);
}
