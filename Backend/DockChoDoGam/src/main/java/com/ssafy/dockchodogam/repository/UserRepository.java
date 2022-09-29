package com.ssafy.dockchodogam.repository;

import com.ssafy.dockchodogam.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    @Query(nativeQuery = true, value =
            "select u.* from users u join friend f on f.user_id2 = u.user_id where f.user_id1 = :id"
    )
    Page<User> findAllFriendByUserId(Pageable pageable, Long id);
    @Query(nativeQuery = true, value =
            "select * from users u where u.user_id != :userId and u.rank_point between :low and :high order by RAND() limit 3")
    List<User> findUsersByRank_pointBetween(Long userId, int low, int high);

    @Query(nativeQuery = true, value = "select * from users u where u.user_id != :userId order by ABS(u.rank_point - :rank_point) limit 3")
    List<User> findUsersOrderByRank_Point(Long userId, int rank_point);

    Long countUsersByRankPointGreaterThan(int rankPoint);

    List<User> findTop5ByOrderByRankPointDescUserIdAsc();

}
