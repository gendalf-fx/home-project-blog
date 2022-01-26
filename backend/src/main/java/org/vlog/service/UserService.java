package org.vlog.service;

import org.springframework.stereotype.Service;
import org.vlog.dto.PasswordDto;
import org.vlog.dto.RoleDto;
import org.vlog.dto.UserDto;

import java.util.List;


public interface UserService {
    UserDto createUser(UserDto userDto);

    List<UserDto> getAllUsers(String sort, Integer page_num, Integer page_size);

    UserDto getUserById(Long id);

    UserDto updateUser(UserDto userDto, Long id);

    void removeUser(Long id);

    RoleDto getUserRoleById(Long id);

    RoleDto updateUserRole(RoleDto roleDto, Long id);

    UserDto changePassword(PasswordDto passwordDto, Long id);

    UserDto getUserByName(String name);
}
