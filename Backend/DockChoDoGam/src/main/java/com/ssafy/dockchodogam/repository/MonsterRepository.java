package com.ssafy.dockchodogam.repository;

import com.ssafy.dockchodogam.domain.Monster;
import com.ssafy.dockchodogam.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MonsterRepository extends JpaRepository<Monster, Long> {

    @Override
    Page<Monster> findAll(Pageable pageable);

    Monster findMonsterByMonsterId(Long monsterId);

    @Query(nativeQuery = true, value = "select * from monster m order by RAND() limit 1")
    Monster findRandomMonster();

    @Query(nativeQuery = true, value = "select * from monster m where m.grade = :grade order by RAND() limit 1")
    Monster findRandomMonster(int grade);

}
