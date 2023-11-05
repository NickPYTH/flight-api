package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.RequestState;

@Repository
public interface RequestStateRepository extends JpaRepository<RequestState, Long> {

}
