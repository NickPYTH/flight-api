package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.AirWorkType;

@Repository
public interface AirWorkTypeRepository extends JpaRepository<AirWorkType, Long> {

}
