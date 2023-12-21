package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.FlightFact;
import ru.sgp.model.FlightPlan;
import ru.sgp.model.RouteFact;
import ru.sgp.model.RoutePlan;

import java.util.List;

@Repository
public interface FlightFactRepository extends JpaRepository<FlightFact, Long> {

    List<FlightFact> findAllByIdRoute(RouteFact route);

    List<FlightFact> findAllByIdRouteOrderById(RouteFact route);
}
