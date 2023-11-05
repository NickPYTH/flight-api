package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.Airport;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

}
