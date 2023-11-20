package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.WorkType;

@Repository
public interface WorkTypeRepository extends JpaRepository<WorkType, Long> {

}
