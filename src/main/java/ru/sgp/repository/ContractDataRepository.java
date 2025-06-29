package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.ContractData;

@Repository
public interface ContractDataRepository extends JpaRepository<ContractData, Long> {

}
