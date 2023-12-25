package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.RequestH;

import java.util.List;

@Repository
public interface RequestHRepository extends JpaRepository<RequestH, Long> {
    List<RequestH> findAllByYear(Integer year);
}
