package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.State;
import ru.practicum.explorewithme.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface EventsRepository extends JpaRepository<Event, Long> {

    List<Event> findEventsByInitiatorId(Long initiatorId, Pageable pageable);

    List<Event> findEventsByInitiatorIn(List<User> initiators, Pageable pageable);

    List<Event> findEventsByIdIn(List<Long> ids);

    @Query("select e from Event e " +
            "where e.initiator.id = ?1 " +
            "and e.category.id = ?2 ")
    List<Event> searchEventsByInitiatorsAndCategories(List<Long> users,
                                                      List<Long> categories,
                                                      Pageable pageable);

    @Query("select e from Event e " +
            "where e.initiator.id = ?1 " +
            "and e.state = ?2 " +
            "and e.category.id = ?3 " +
            "and e.eventDate between ?4 and ?5 ")
    List<Event> searchEventsByAdminAndStatesAndCategoriesAndRange(List<Long> users,
                                                                  List<State> stateList,
                                                                  List<Long> categories,
                                                                  LocalDateTime starrDateTime,
                                                                  LocalDateTime endDateTime,
                                                                  Pageable pageable);

    @Query("select e from Event e " +
            "where lower(e.annotation) like lower(concat('%', ?1, '%')) " +
            "or lower(e.description) like lower(concat('%', ?1, '%')) " +
            "and e.state = 'PUBLISHED' " +
            "and e.category.id = ?2 " +
            "and e.paid = ?3 " +
            "and e.eventDate between ?4 and ?5 " +
            "order by e.eventDate asc")
    List<Event> searchEventsByTextAndCategoriesAndPaidBySortByEventDay(String text,
                                                                       List<Long> category,
                                                                       Boolean paid,
                                                                       LocalDateTime rangeStart,
                                                                       LocalDateTime rangeEnd,
                                                                       Pageable pageable);

    @Query("select e from Event e " +
            "where lower(e.annotation) like lower(concat('%', ?1, '%')) " +
            "or lower(e.description) like lower(concat('%', ?1, '%')) " +
            "and e.state = 'PUBLISHED' " +
            "and e.category.id = ?2 " +
            "and e.paid = ?3 " +
            "and e.eventDate between ?4 and ?5 " +
            "order by e.views asc")
    List<Event> searchEventsByTextAndCategoriesAndPaidBySortByViews(String text,
                                                                    List<Long> category,
                                                                    Boolean paid,
                                                                    LocalDateTime rangeStart,
                                                                    LocalDateTime rangeEnd,
                                                                    Pageable pageable);

    @Query("select e from Event e " +
            "where (lower(e.annotation) like lower(concat('%', ?1, '%')) " +
            "or lower(e.description) like lower(concat('%', ?1, '%'))) " +
            "and e.state = 'PUBLISHED'" +
            "order by e.views asc ")
    List<Event> searchEventsByTextSortByViews(String text, Pageable pageable);

    @Query("select e from Event e " +
            "where (lower(e.annotation) like lower(concat('%', ?1, '%')) " +
            "or lower(e.description) like lower(concat('%', ?1, '%'))) " +
            "and e.state = 'PUBLISHED'" +
            "order by e.eventDate asc ")
    List<Event> searchEventsByTextSortByEvents(String text, Pageable pageable);
}
