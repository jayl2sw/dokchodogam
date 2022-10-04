package com.ssafy.dockchodogam.repository;


import com.ssafy.dockchodogam.domain.Archive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    Page<Archive> findArchivesByUserNicknameOrderByArchiveIdDesc(Pageable pageable, @Param("nickname") String nickname);

}
