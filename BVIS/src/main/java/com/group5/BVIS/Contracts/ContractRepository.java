package com.group5.BVIS.Contracts;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for the JPA repository ContractRepository to access the H2 database.
 */
@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

	List<Contract> findBycustomerId(String customerId);

}