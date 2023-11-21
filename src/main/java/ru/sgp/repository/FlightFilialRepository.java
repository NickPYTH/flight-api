package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.FlightFilial;
import ru.sgp.model.RouteFilial;

import java.util.List;

@Repository
public interface FlightFilialRepository extends JpaRepository<FlightFilial, Long> {

    List<FlightFilial> findAllByIdRoute(RouteFilial route);

    List<FlightFilial> findAllByIdRouteOrderById(RouteFilial route);
}
