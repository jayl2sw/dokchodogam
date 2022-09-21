package com.ssafy.dockchodogam.repository;

import com.ssafy.dockchodogam.domain.Propose;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProposeRepository extends JpaRepository<Propose, Long> {
    @Query(nativeQuery = true, value =
        "select * from propose where user_id2 = :id"
    )
    Page<Propose> findAllByReceiver(Pageable pageable, Long id);
}
