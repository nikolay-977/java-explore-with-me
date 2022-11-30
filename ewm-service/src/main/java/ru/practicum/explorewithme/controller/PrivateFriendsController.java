package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.dto.EventShortDto;
import ru.practicum.explorewithme.service.PrivateFriendsService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/friends")
@Slf4j
@RequiredArgsConstructor
public class PrivateFriendsController {

    private final PrivateFriendsService privateFriendsService;

    @PostMapping("/{friendId}")
    public void addFriends(@PathVariable Long userId, @PathVariable Long friendId) {
        log.info("Send request Post: add friendship for userId={}, friendId={}", userId, friendId);
        privateFriendsService.addFriends(userId, friendId);
    }

    @DeleteMapping("/{friendId}")
    public void removeFriends(@PathVariable Long userId, @PathVariable Long friendId) {
        log.info("Send request Delete: delete friendship for userId={}, friendId={}", userId, friendId);
        privateFriendsService.removeFriends(userId, friendId);
    }

    @GetMapping("/{friendId}")
    public List<EventShortDto> getFriendEvents(@PathVariable Long userId,
                                               @PathVariable Long friendId,
                                               @RequestParam(name = "from", defaultValue = "0") Integer from,
                                               @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Send request GET: get events with userId={}, friendId={}, from={}, size={}",
                userId, friendId, from, size);
        return privateFriendsService.getFriendEvents(userId, friendId, from, size);
    }

    @GetMapping
    public List<EventShortDto> getAllFriendsEvents(@PathVariable Long userId,
                                                   @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                   @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Send request GET: get events with userId={}, from={}, size={}",
                userId, from, size);
        return privateFriendsService.getAllFriendsEvents(userId, from, size);
    }
}
