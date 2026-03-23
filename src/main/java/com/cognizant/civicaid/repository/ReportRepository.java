package com.civicaid.repository;

import com.civicaid.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Page<Report> findByScope(Report.ReportScope scope, Pageable pageable);
    Page<Report> findByGeneratedBy_UserId(Long userId, Pageable pageable);
}
