package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.dto.CompilationDto;
import ru.practicum.explorewithme.model.dto.NewCompilationDto;
import ru.practicum.explorewithme.service.AdminCompilationsService;


@RestController
@RequestMapping(path = "/admin/compilations")
@Slf4j
@RequiredArgsConstructor
public class AdminCompilationsController {

    private final AdminCompilationsService adminCompilationsService;

    @PostMapping
    public CompilationDto addCompilation(@Validated @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Send request POST: add compilation newCompilationDto={}", newCompilationDto);
        return adminCompilationsService.addCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("Send request POST: delete compilation by compId={}", compId);
        adminCompilationsService.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEvent(@PathVariable Long compId,
                            @PathVariable Long eventId) {
        log.info("Send request POST: delete event by compId={}, eventId={}", compId, eventId);
        adminCompilationsService.deleteEvent(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void updateEvent(@PathVariable Long compId,
                            @PathVariable Long eventId) {
        log.info("Send request PATCH: patch event for compilation compId={}, eventId={}", compId, eventId);
        adminCompilationsService.updateEvent(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable Long compId) {
        log.info("Send request DELETE: unpin compilation by compId={}", compId);
        adminCompilationsService.unpinCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable Long compId) {
        log.info("Send request PATCH: pin compilation by compId={}", compId);
        adminCompilationsService.pinCompilation(compId);
    }
}
