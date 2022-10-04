package com.ssafy.dockchodogam.repository;

import com.ssafy.dockchodogam.domain.Monster;
import com.ssafy.dockchodogam.domain.User;
import com.ssafy.dockchodogam.domain.UserMonster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserMonsterRepository extends JpaRepository<UserMonster, Long> {
    Optional<UserMonster> findUserMonsterByMonsterMonsterIdAndUserUserId(Long monsterId, Long userId);

    Optional<UserMonster> findUserMonsterByMonsterAndUser(Monster monster, User user);

    List<UserMonster> findUserMonstersByUserUserIdOrderByMonsterGradeDesc(Long userId);

}
