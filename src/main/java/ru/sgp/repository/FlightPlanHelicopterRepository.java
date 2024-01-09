package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.FlightPlanHelicopter;
import ru.sgp.model.RoutePlanHelicopter;

import java.util.List;

@Repository
public interface FlightPlanHelicopterRepository extends JpaRepository<FlightPlanHelicopter, Long> {

    List<FlightPlanHelicopter> findAllByIdRouteHelicopterOrderById(RoutePlanHelicopter route);
}
