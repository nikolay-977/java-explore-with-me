package ru.practicum.explorewithme.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static ru.practicum.explorewithme.model.State.PENDING;

@Entity
@Table(name = "requests")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequest {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @JoinColumn(nullable = false)
    @Enumerated(STRING)
    @Builder.Default
    private State status = PENDING;
}
