package ru.sgp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgp.model.Request;
import ru.sgp.model.RequestHistory;

import java.util.List;

@Repository
public interface RequestHistoryRepository extends JpaRepository<RequestHistory, Long> {

    List<RequestHistory> findAllByRequestOrderByDateDesc(Request request);
}
