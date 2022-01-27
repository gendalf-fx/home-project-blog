package org.vlog.service.impl;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.vlog.dto.PasswordDto;
import org.vlog.dto.RoleDto;
import org.vlog.dto.UserDto;
import org.vlog.entity.RoleEntity;
import org.vlog.entity.UserEntity;
import org.vlog.exception.custom.GlobalNotFoundException;
import org.vlog.exception.custom.GlobalValidationException;
import org.vlog.mapper.UserMapper;
import org.vlog.repository.UserRepository;
import org.vlog.service.UserService;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = userMapper.userToEntity(userDto);
        userRepository.save(userEntity);
        return userMapper.userToDto(userEntity);
    }

    @Override
    public List<UserDto> getAllUsers(String sort, Integer page_num, Integer page_size) {
        String checkedSort = Optional.ofNullable(sort).orElse("-id");
        Integer checkedPageNum = Optional.ofNullable(page_num).orElse(0);
        Integer checkedSize = Optional.ofNullable(page_size).orElse(10);
        Sort.Direction direction = null;
        if (checkedSort.contains("-")) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.DEFAULT_DIRECTION;
        }
        String sortedString = checkedSort.substring(1, checkedSort.length() - 1);
        Pageable pageable = PageRequest.of(checkedPageNum, checkedSize, Sort.by(sortedString).and(Sort.by(direction)));
        Page<UserEntity> userEntities = userRepository.findAll(pageable);
        return userMapper.userListToDto(userEntities);
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new GlobalNotFoundException("Unauthorized"));
        return userMapper.userToDto(userEntity);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
//        UserEntity userEntity = getUserEntityById(id);
//        userEntity = UserEntity.builder()
//                .name(userDto.getName())
//                .firstName(userDto.getFirstName())
//                .lastName(userDto.getLastName())
//                .email(userDto.getEmail())
//                .password(userEntity.getPassword())
//                .build();
//        userRepository.save(userEntity);
//        return userMapper.userToDto(userEntity);
        UserDto userById = getUserById(id);
        UserEntity userEntity = userMapper.updateUser(userById, userDto);
        userRepository.save(userEntity);
        return userMapper.userToDto(userEntity);
    }

    @Override
    public void removeUser(Long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(() -> new GlobalNotFoundException("User not found")));
    }

    @Override
    public RoleDto getUserRoleById(Long id) {
        UserDto userDto = getUserById(id);
        if (userDto.getRole() != null) {
            return userDto.getRole();
        } else {
            throw new GlobalNotFoundException("There is no such a role");
        }
    }

    @Override
    public RoleDto updateUserRole(RoleDto roleDto, Long id) {
        UserEntity userEntity = getUserEntityById(id);
        userEntity.setRole(RoleEntity.valueOf(roleDto.name()));
        userRepository.save(userEntity);
        return userMapper.userToDto(userEntity).getRole();
    }


    @Override
    public UserDto changePassword(PasswordDto passwordDto, Long id) {
        UserEntity userEntity = getUserEntityById(id);
        userEntity.setPassword(passwordDto.getNewPassword());
        userRepository.save(userEntity);
        return userMapper.userToDto(userEntity);
    }

    public UserDto getUserByName(String name) {
        UserEntity userEntity = userRepository.findByName(name);
        if (userEntity != null) {
            return userMapper.userToDto(userEntity);
        } else {
            throw new GlobalValidationException("Validation exception");
        }
    }

    private UserEntity getUserEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new GlobalNotFoundException("User not found"));
    }
}
