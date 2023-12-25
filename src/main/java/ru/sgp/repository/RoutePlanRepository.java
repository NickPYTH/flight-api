package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.EmployeeResponsible;
import ru.sgp.model.Request;
import ru.sgp.model.RoutePlan;
import ru.sgp.model.WorkType;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoutePlanRepository extends JpaRepository<RoutePlan, Long> {

    List<RoutePlan> findAllByIdRequest(Request idRequest);

    Optional<RoutePlan> findByIdRequestAndIdEmpRespAndIdWorkType(Request request, EmployeeResponsible employeeResponsible, WorkType workType);
}
