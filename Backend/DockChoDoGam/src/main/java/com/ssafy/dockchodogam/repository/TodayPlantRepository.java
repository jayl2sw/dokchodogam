package com.ssafy.dockchodogam.repository;

import com.ssafy.dockchodogam.domain.TodayPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodayPlantRepository extends JpaRepository<TodayPlant, Long> {
    Optional<TodayPlant> findByMonthAndDay(int month, int day);
}
