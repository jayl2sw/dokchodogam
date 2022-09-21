package com.ssafy.dockchodogam.repository;

import com.ssafy.dockchodogam.domain.Friend;
import com.ssafy.dockchodogam.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query(nativeQuery = true, value =
        "select * from friend where user_id1=:id1 and user_id2=:id2"
    )
    Optional<Friend> findByTwoId(@Param("id1") Long id1, @Param("id2") Long id2);
    @Query(nativeQuery = true, value =
        "select count(*) from friend where user_id1=:id"
    )
    Integer countById(@Param("id") Long id);

    Page<Friend> findByUserA(Pageable pageable, User user);
    List<Friend> findByUserAAndGiftExist(User user, Boolean giftExist);
    List<Friend> findByUserBAndGiftExist(User user, Boolean giftExist);
}
