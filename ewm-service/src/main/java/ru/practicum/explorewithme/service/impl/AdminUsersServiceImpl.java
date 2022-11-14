package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.mapper.UserMapper;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.model.dto.NewUserRequest;
import ru.practicum.explorewithme.model.dto.UserDto;
import ru.practicum.explorewithme.repository.UsersRepository;
import ru.practicum.explorewithme.service.AdminUsersService;
import ru.practicum.explorewithme.utils.CustomPageRequest;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUsersServiceImpl implements AdminUsersService {

    private final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        final Pageable pageable = CustomPageRequest.of(from, size);
        List<User> userList;
        if (ids.isEmpty()) {
            userList = usersRepository.findAll(pageable).toList();
        } else {
            userList = usersRepository.findAllById(ids);
        }
        List<UserDto> userDtoList = UserMapper.toListUserDto(userList);
        log.info("Got users={}", userDtoList);
        return userDtoList;
    }

    @Transactional
    @Override
    public UserDto addUser(NewUserRequest newUserRequest) {
        UserDto userDto = UserMapper.toUserDto(usersRepository.save(UserMapper.toUser(newUserRequest)));
        log.info("Added user={}", userDto);
        return userDto;
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
        log.info("Deleted user, id={}", id);
    }
}
