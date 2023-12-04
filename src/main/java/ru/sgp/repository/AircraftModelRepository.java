package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.AircraftModel;

@Repository
public interface AircraftModelRepository extends JpaRepository<AircraftModel, Long> {

}
