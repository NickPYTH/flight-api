package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.RequestH;
import ru.sgp.model.RoutePlanHelicopter;

import java.util.List;

@Repository
public interface RoutePlanHelicopterRepository extends JpaRepository<RoutePlanHelicopter, Long> {

    List<RoutePlanHelicopter> findAllByIdRequestH(RequestH requestH);
}
