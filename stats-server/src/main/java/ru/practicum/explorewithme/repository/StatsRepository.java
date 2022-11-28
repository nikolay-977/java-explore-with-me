package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query("select e " +
            "from EndpointHit e " +
            "where e.timestamp between ?1 and ?2 ")
    List<EndpointHit> searchEndpointHit(LocalDateTime start, LocalDateTime end);

    @Query("select e " +
            "from EndpointHit e " +
            "where e.uri = ?1 " +
            "and e.timestamp between ?2 and ?3 ")
    List<EndpointHit> searchEndpointHitByUri(List<String> uri, LocalDateTime start, LocalDateTime end);

    @Query("select distinct e " +
            "from EndpointHit e " +
            "where e.timestamp between ?1 and ?2 ")
    List<EndpointHit> searchUniqueEndpointHit(LocalDateTime start, LocalDateTime end);

    @Query("select distinct e " +
            "from EndpointHit e " +
            "where e.uri = ?1 ")
    List<EndpointHit> searchUniqueEndpointHitByUri(List<String> uri, LocalDateTime start, LocalDateTime end);
}
