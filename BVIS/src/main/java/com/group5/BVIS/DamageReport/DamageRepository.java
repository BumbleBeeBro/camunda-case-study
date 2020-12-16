package com.group5.BVIS.DamageReport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for the JPA repository DamageRepository to access the H2 database.
 */
@Repository
public interface DamageRepository extends JpaRepository<DamageReport, Long> {

}