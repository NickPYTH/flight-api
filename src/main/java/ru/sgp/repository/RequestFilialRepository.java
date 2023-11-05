package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.RequestFilial;

import java.util.List;

@Repository
public interface RequestFilialRepository extends JpaRepository<RequestFilial, Long> {
    List<RequestFilial> findAllByYear(Integer year);
}
