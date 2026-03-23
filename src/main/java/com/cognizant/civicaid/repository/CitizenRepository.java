package com.cognizant.civicaid.repository;

import com.cognizant.civicaid.entity.Citizen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    Optional<Citizen> findByUser_UserId(Long userId);
    Page<Citizen> findByStatus(Citizen.CitizenStatus status, Pageable pageable);
    boolean existsByUser_UserId(Long userId);

    @Query("SELECT c FROM Citizen c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Citizen> searchByName(String name, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Citizen c WHERE c.status = :status")
    long countByStatus(Citizen.CitizenStatus status);
}
