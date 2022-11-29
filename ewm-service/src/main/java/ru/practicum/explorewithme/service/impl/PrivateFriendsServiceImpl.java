package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.exception.BadRequestException;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.Friendship;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.model.dto.EventShortDto;
import ru.practicum.explorewithme.repository.EventsRepository;
import ru.practicum.explorewithme.repository.FriendshipRepository;
import ru.practicum.explorewithme.repository.UsersRepository;
import ru.practicum.explorewithme.service.PrivateFriendsService;
import ru.practicum.explorewithme.utils.CustomPageRequest;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.utils.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateFriendsServiceImpl implements PrivateFriendsService {

    private final UsersRepository usersRepository;

    private final FriendshipRepository friendshipRepository;

    private final EventsRepository eventsRepository;

    @Override
    public void addFriends(Long userId, Long friendId) {
        isFriendValid(userId, friendId);
        checkFriendshipExist(userId, friendId);
        User user = findUser(userId);
        User friend = findUser(friendId);
        Friendship friendship = Friendship.builder()
                .user(user)
                .friend(friend)
                .build();
        friendshipRepository.save(friendship);
        log.info("Added friendship={}", friendship);
    }

    @Override
    public void removeFriends(Long userId, Long friendId) {
        isFriendValid(userId, friendId);
        Friendship friendship = findFriendship(userId, friendId);
        friendshipRepository.delete(friendship);
        log.info("Deleted friendship={}", friendship);
    }

    @Override
    public List<EventShortDto> getAllFriendsEvents(Long userId, Integer from, Integer size) {
        final Pageable pageable = CustomPageRequest.of(from, size);
        List<User> initiators = friendshipRepository.findFriendshipByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Friends not found"))
                .stream()
                .map(Friendship::getFriend)
                .collect(Collectors.toList());

        List<Event> eventList = eventsRepository.findEventsByInitiatorIn(initiators, pageable);
        log.info("Got friend events={} by userId={}", eventList, userId);
        return EventMapper.toEventShortDtoList(eventList);
    }

    @Override
    public List<EventShortDto> getFriendEvents(Long userId, Long friendId, Integer from, Integer size) {
        final Pageable pageable = CustomPageRequest.of(from, size);
        findUser(userId);
        findUser(friendId);
        isFriendValid(userId, friendId);
        findFriendship(userId, friendId);
        List<Event> eventList = eventsRepository.findEventsByInitiatorId(friendId, pageable);
        log.info("Got friend events={} by userId={}, friendId={}", eventList, userId, friendId);
        return EventMapper.toEventShortDtoList(eventList);
    }

    private void isFriendValid(Long userId, Long friendId) {
        if (userId.equals(friendId)) {
            throw new BadRequestException("The user and friend IDs must be different");
        }
    }

    private User findUser(Long id) {
        return usersRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format(PATTERN_TWO_ARGS, USER_NOT_FOUND_MESSAGE, id)));
    }

    private Friendship findFriendship(Long userId, Long friendId) {
        return friendshipRepository
                .findFriendshipByUserIdAndAndFriendId(userId, friendId)
                .orElseThrow(() -> new BadRequestException(MessageFormat.format(FRIENDSHIP_NOT_FOUND_MESSAGE, userId, friendId)));
    }

    private void checkFriendshipExist(Long userId, Long friendId) {
        if (friendshipRepository.findFriendshipByUserIdAndAndFriendId(userId, friendId).isPresent()) {
            throw new BadRequestException(MessageFormat.format(FRIENDSHIP_ALREADY_EXIST_MESSAGE, userId, friendId));
        }
    }
}
