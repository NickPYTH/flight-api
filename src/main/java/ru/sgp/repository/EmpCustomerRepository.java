package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.EmpCustomer;

@Repository
public interface EmpCustomerRepository extends JpaRepository<EmpCustomer, Long> {

}
