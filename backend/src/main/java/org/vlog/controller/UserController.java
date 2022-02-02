package org.vlog.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vlog.dto.PasswordDto;
import org.vlog.dto.RoleDto;
import org.vlog.dto.UserDto;
import org.vlog.dto.UserDtoDeserializer;
import org.vlog.exception.custom.GlobalNotFoundException;
import org.vlog.service.UserService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Data
@RestController
@RequestMapping(path = "/users")

public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/current")
    public @ResponseBody
    UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/current")
    public @ResponseBody
    UserDto updateCurrentUser(@RequestBody UserDto userDto) {
       return userService.updateCurrentUser(userDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/current/password")
    public @ResponseBody
    UserDto updatePassword(@RequestBody PasswordDto passwordDto) {
        return userService.updatePassword(passwordDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public @ResponseBody
    UserDto createUser(@Validated @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public @ResponseBody
    @PreAuthorize("hasAuthority('admin')")
    List<UserDto> getAllUsers(@RequestParam(required = false) Long id, @RequestParam(required = false) String name,
                              @RequestParam(defaultValue = "-id") String sort,
                              @RequestParam(defaultValue = "0") Integer page_num,
                              @RequestParam(defaultValue = "10") Integer page_size) {
        return userService.getAllUsers(id, name, sort, page_num, page_size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('admin')")
    public @ResponseBody
    UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('admin')")
    public @ResponseBody
    UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.updateUser(userDto, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("{id}")
    public void removeUser(@PathVariable Long id) {
        userService.removeUser(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}/role")
    @PreAuthorize("hasAuthority('admin')")
    public @ResponseBody
    RoleDto getUserRoleById(@PathVariable Long id) {
        return userService.getUserRoleById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}/role")
    @PreAuthorize("hasAuthority('admin')")
    public @ResponseBody
    RoleDto updateUserRole(@PathVariable Long id, @RequestBody UserDtoDeserializer userDtoDeserializer) {
        RoleDto.RoleEnum roleDto = Optional.of
                (RoleDto.RoleEnum.valueOf(userDtoDeserializer.getName().toUpperCase(Locale.ROOT)))
                .orElseThrow(() -> new GlobalNotFoundException("Role with name: "
                        + userDtoDeserializer.getName().toUpperCase(Locale.ROOT) + " doesn`t exist!"));
        return userService.updateUserRole(roleDto, id);
    }


}
