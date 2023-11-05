package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.RequestFilial;
import ru.sgp.model.RouteFilial;

import java.util.List;

@Repository
public interface RouteFilialRepository extends JpaRepository<RouteFilial, Long> {

    List<RouteFilial> findAllByIdRequest(RequestFilial idRequest);
}
