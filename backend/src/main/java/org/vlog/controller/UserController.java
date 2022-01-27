package org.vlog.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vlog.dto.RoleDto;
import org.vlog.dto.UserDto;
import org.vlog.service.UserService;

import java.util.List;

@Data
@RestController
@RequestMapping(path = "/users")

public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public @ResponseBody
    UserDto createUser(@Validated @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public @ResponseBody
    List<UserDto> getAllUsers(Integer id, String name,
                              String sort, Integer page_num, Integer page_size) {
        return userService.getAllUsers(sort, page_num, page_size);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("{id}")
    public @ResponseBody
    UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("{id}")
    public @ResponseBody
    UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.updateUser(userDto, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void removeUser(@PathVariable Long id) {
        userService.removeUser(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}/role")
    public @ResponseBody
    RoleDto getUserRoleById(@PathVariable Long id) {
        return userService.getUserRoleById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}/role")
    public @ResponseBody
    RoleDto updateUserRole(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        return userService.updateUserRole(roleDto, id);
    }


}
