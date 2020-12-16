package com.group5.BVIS.Cars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for the JPA repository CarRepository to access the H2 database.
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}