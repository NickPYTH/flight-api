package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.EmployeeResponsible;

@Repository
public interface EmployeeResponsibleRepository extends JpaRepository<EmployeeResponsible, Long> {

}
