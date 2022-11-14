package ru.practicum.explorewithme.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "compilations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Compilation {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "compilations_events",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events = new HashSet<>();

    @Column(name = "is_pinned", nullable = false)
    private boolean pinned;

    @Column(nullable = false)
    private String title;
}
