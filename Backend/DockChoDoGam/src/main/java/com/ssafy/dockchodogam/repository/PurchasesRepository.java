package com.ssafy.dockchodogam.repository;

import com.ssafy.dockchodogam.domain.Purchases;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasesRepository extends JpaRepository<Purchases, Long> {
}
