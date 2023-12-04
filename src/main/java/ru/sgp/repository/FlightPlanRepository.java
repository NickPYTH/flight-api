package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.FlightPlan;
import ru.sgp.model.RoutePlan;

import java.util.List;

@Repository
public interface FlightPlanRepository extends JpaRepository<FlightPlan, Long> {

    List<FlightPlan> findAllByIdRoute(RoutePlan route);

    List<FlightPlan> findAllByIdRouteOrderById(RoutePlan route);
}
