package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.AircraftType;

@Repository
public interface AircraftTypeRepository extends JpaRepository<AircraftType, Long> {

}
