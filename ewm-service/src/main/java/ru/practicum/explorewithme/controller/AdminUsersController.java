package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.dto.NewUserRequest;
import ru.practicum.explorewithme.model.dto.UserDto;
import ru.practicum.explorewithme.service.AdminUsersService;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
@RequiredArgsConstructor
public class AdminUsersController {

    private final AdminUsersService adminUsersService;

    @GetMapping()
    public List<UserDto> getUsers(@RequestParam(name = "ids", required = false) List<Long> ids,
                                  @RequestParam(name = "from", defaultValue = "0") Integer from,
                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Send request GET: get users by ids {}", ids);
        return adminUsersService.getUsers(ids, from, size);
    }

    @PostMapping
    public UserDto addUser(@Validated @RequestBody NewUserRequest newUserRequest) {
        log.info("Send request POST: add user {}", newUserRequest);
        return adminUsersService.addUser(newUserRequest);
    }

    @DeleteMapping(path = "/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("Send request DELETE: delete users by id: {}", userId);
        adminUsersService.deleteUser(userId);
    }
}
