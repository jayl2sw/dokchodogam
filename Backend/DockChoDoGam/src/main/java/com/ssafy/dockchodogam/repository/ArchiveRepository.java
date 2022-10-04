package com.ssafy.dockchodogam.repository;


import com.ssafy.dockchodogam.domain.Archive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    Page<Archive> findArchivesBy(Pageable pageable);

}
