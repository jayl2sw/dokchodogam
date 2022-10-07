package com.ssafy.dockchodogam.repository;

import com.ssafy.dockchodogam.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
