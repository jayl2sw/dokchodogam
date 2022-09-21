package com.ssafy.dockchodogam.repository;

import com.ssafy.dockchodogam.domain.Deck;
import com.ssafy.dockchodogam.domain.Monster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeckRepository extends JpaRepository<Deck, Long> {

    Optional<Deck> findDeckByUserUserId(Long userId);

}
