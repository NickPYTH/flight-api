package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.Request;
import ru.sgp.model.RequestCost;

import java.util.List;

@Repository
public interface RequestCostRepository extends JpaRepository<RequestCost, Long> {

    List<RequestCost> findAllByRequest(Request request);
}
