package org.vlog.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vlog.dto.*;
import org.vlog.exception.custom.GlobalNotFoundException;
import org.vlog.service.CommentService;
import org.vlog.service.UserService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Data
@RestController
@RequestMapping(path = "/users")

public class UserController {

    public UserController(UserService userService, CommentService commentService) {
        this.userService = userService;
        this.commentService = commentService;
    }

    private final UserService userService;
    private final CommentService commentService;

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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/current/comments")
    public List<CommentDto> getCommentsByCurrentUser(@RequestParam(defaultValue = "-id") String sort,
                                                     @RequestParam(defaultValue = "0") Integer page_num,
                                                     @RequestParam(defaultValue = "10") Integer page_size) {
        return commentService.getCommentsByCurrentUser(sort, page_num, page_size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/current/comments/{id}")
    public CommentDto getCommentsByIdAndCurrentUser(
            @PathVariable Long id,
            @RequestParam(defaultValue = "-id") String sort,
            @RequestParam(defaultValue = "0") Integer page_num,
            @RequestParam(defaultValue = "10") Integer page_size) {
        return commentService.getCommentByIdAndCurrentUser(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/current/comments/{id}")
    public CommentDto updateCommentByIdAndCurrentUser(
            @PathVariable Long id,
            @RequestBody CommentDto commentDto) {
        return commentService.updateCommentByIdAndCurrentUser(id, commentDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/current/comments/{id}")
    public void removeCommentByIdAndCurrentUser(@PathVariable Long id) {
        commentService.removeCommentByIdAndCurrentUser(id);
    }

}
