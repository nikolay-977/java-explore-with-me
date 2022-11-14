package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.dto.CompilationDto;
import ru.practicum.explorewithme.service.PublicCompilationsService;

import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@Slf4j
@RequiredArgsConstructor
public class PublicCompilationsController {
    private final PublicCompilationsService publicCompilationsService;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                                @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Send request GET: get events by pinned={}, from={}, size={}",
                pinned, from, size);
        return publicCompilationsService.getCompilation(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable Long compId) {
        log.info("Send request GET: get compilation by compId={}", compId);
        return publicCompilationsService.getCompilationById(compId);
    }

}
