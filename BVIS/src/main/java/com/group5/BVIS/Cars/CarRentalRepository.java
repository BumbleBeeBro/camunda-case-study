package com.group5.BVIS.Cars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for the JPA repository CarRentalRepository to access the H2 database.
 */
@Repository
public interface CarRentalRepository extends JpaRepository<CarRental, Long> {

}