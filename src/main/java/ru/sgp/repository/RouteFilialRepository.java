package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.EmployeeResponsible;
import ru.sgp.model.RequestFilial;
import ru.sgp.model.RouteFilial;
import ru.sgp.model.WorkType;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteFilialRepository extends JpaRepository<RouteFilial, Long> {

    List<RouteFilial> findAllByIdRequest(RequestFilial idRequest);

    Optional<RouteFilial> findByIdRequestAndIdEmpRespAndIdWorkType(RequestFilial requestFilial, EmployeeResponsible employeeResponsible, WorkType workType);
}
