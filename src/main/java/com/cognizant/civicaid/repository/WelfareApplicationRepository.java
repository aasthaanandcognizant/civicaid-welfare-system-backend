package com.cognizant.civicaid.repository;

import com.cognizant.civicaid.entity.WelfareApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WelfareApplicationRepository extends JpaRepository<WelfareApplication, Long> {
    Page<WelfareApplication> findByCitizen_CitizenId(Long citizenId, Pageable pageable);
    Page<WelfareApplication> findByProgram_ProgramId(Long programId, Pageable pageable);
    Page<WelfareApplication> findByStatus(WelfareApplication.ApplicationStatus status, Pageable pageable);
    boolean existsByCitizen_CitizenIdAndProgram_ProgramId(Long citizenId, Long programId);

    @Query("SELECT COUNT(a) FROM WelfareApplication a WHERE a.status = :status")
    long countByStatus(WelfareApplication.ApplicationStatus status);

    @Query("SELECT COUNT(a) FROM WelfareApplication a WHERE a.submittedDate BETWEEN :from AND :to")
    long countBySubmittedDateBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT a FROM WelfareApplication a WHERE a.citizen.citizenId = :citizenId AND a.status = :status")
    List<WelfareApplication> findByCitizenAndStatus(Long citizenId, WelfareApplication.ApplicationStatus status);
}
