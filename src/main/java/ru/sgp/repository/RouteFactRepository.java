package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.Request;
import ru.sgp.model.RouteFact;
import ru.sgp.model.RoutePlan;

import java.util.List;

@Repository
public interface RouteFactRepository extends JpaRepository<RouteFact, Long> {

    List<RouteFact> findAllByIdRequest(Request idRequest);
}
