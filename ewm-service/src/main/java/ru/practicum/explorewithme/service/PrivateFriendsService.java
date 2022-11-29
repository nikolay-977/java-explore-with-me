package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.model.dto.EventShortDto;

import java.util.List;

public interface PrivateFriendsService {
    void addFriends(Long userId, Long friendId);

    void removeFriends(Long userId, Long friendId);

    List<EventShortDto> getAllFriendsEvents(Long userId, Integer from, Integer size);

    List<EventShortDto> getFriendEvents(Long userId, Long friendId, Integer from, Integer size);
}
