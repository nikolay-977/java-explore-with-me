package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.model.dto.NewUserRequest;
import ru.practicum.explorewithme.model.dto.UserDto;

import java.util.List;

public interface AdminUsersService {
    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto addUser(NewUserRequest userDto);

    void deleteUser(Long userId);
}
