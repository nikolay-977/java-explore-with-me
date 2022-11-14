package ru.practicum.explorewithme.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;
import static ru.practicum.explorewithme.model.State.PENDING;

@Entity
@Table(name = "events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "confirmed_requests")
    @Builder.Default
    private Long confirmedRequests = 0L;
    @Column(name = "created_on")
    @Builder.Default
    private LocalDateTime createdOn = LocalDateTime.now();
    @Column(nullable = false)
    private String description;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @OneToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "is_paid", nullable = false)
    private Boolean paid;

    @Column(name = "participant_limit", nullable = false)
    @Builder.Default
    private Long participantLimit = 0L;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "is_request_moderation", nullable = false)
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State state = PENDING;

    @Column(nullable = false)
    private String title;

    @Builder.Default
    private Long views = 0L;
}