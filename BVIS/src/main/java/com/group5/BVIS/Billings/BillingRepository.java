package com.group5.BVIS.Billings;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for the JPA repository BillingRepository, to access the H2 database.
 */
@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

}