package com.ssafy.dockchodogam.repository;

import com.ssafy.dockchodogam.domain.Propose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProposeRepository extends JpaRepository<Propose, Long> {
    @Query(nativeQuery = true, value =
        "select * from propose where user_id2 = :id"
    )
    List<Propose> findAllByReceiver(Long id);

    @Query(nativeQuery = true, value =
            "select * from propose where user_id1=:id1 and user_id2=:id2"
    )
    Optional<Propose> findByTwoId(@Param("id1") Long id1, @Param("id2") Long id2);
}
