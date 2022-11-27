package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.CompilationMapper;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.dto.CompilationDto;
import ru.practicum.explorewithme.repository.CompilationsRepository;
import ru.practicum.explorewithme.service.PublicCompilationsService;
import ru.practicum.explorewithme.utils.CustomPageRequest;

import java.text.MessageFormat;
import java.util.List;

import static ru.practicum.explorewithme.utils.Constants.COMPILATION_NOT_FOUND_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicCompilationsServiceImpl implements PublicCompilationsService {

    private final CompilationsRepository compilationsRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CompilationDto> getCompilation(Boolean pinned, Integer from, Integer size) {
        final Pageable pageable = CustomPageRequest.of(from, size);
        List<Compilation> compilationList = pinned != null ? compilationsRepository.findAllByPinned(pinned, pageable)
                : compilationsRepository.findAll(pageable).toList();
        List<CompilationDto> compilationDtoList = CompilationMapper.toCompilationDtoList(compilationList);
        log.info("Got compilations={}", compilationDtoList);
        return compilationDtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = compilationsRepository.findById(compId).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("{0}{1}", COMPILATION_NOT_FOUND_MESSAGE, compId)));
        CompilationDto compilationDto = CompilationMapper.toCompilationDto(compilation);
        log.info("Got compilations={}", compilationDto);
        return compilationDto;
    }
}
