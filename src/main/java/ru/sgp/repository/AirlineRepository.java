package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.Airline;
import ru.sgp.model.Airport;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {

}
