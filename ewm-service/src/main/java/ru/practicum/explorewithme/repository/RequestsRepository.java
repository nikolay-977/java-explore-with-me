package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.model.ParticipationRequest;
import ru.practicum.explorewithme.model.State;

import java.util.List;

public interface RequestsRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findAllByRequesterId(Long userId);

    ParticipationRequest findAllByRequesterIdAndIdAndStatusNot(Long userId, Long requestId, State state);

    @Query("select pr " +
            "from ParticipationRequest pr " +
            "join Event e " +
            "on pr.event.id = e.id " +
            "where e.id = ?1 " +
            "and e.initiator.id = ?2 ")
    List<ParticipationRequest> searchRequestsByUserIdAndEventId(Long userId, Long eventId);
}
