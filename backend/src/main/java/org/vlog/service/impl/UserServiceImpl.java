package org.vlog.service.impl;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vlog.dto.PasswordDto;
import org.vlog.dto.RoleDto;
import org.vlog.dto.UserDto;
import org.vlog.entity.RoleEntity;
import org.vlog.entity.UserEntity;
import org.vlog.exception.custom.GlobalNotFoundException;
import org.vlog.exception.custom.GlobalValidationException;
import org.vlog.mapper.UserMapper;
import org.vlog.repository.RoleRepository;
import org.vlog.repository.UserRepository;
import org.vlog.security.SecurityUser;
import org.vlog.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class UserServiceImpl implements UserService {

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        userRepository.save(new UserEntity("admin", "admin", passwordEncoder.encode("admin"),
                "admin", "admin", new RoleEntity(null, RoleEntity.RoleEnum.ADMIN)));
    }

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = userMapper.userToEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(new RoleEntity(null, RoleEntity.RoleEnum.BLOGGER));
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
            checkedSort = checkedSort.substring(1, checkedSort.length());
        } else {
            direction = Sort.DEFAULT_DIRECTION;

        }
        Pageable pageable = PageRequest.of(checkedPageNum, checkedSize, Sort.by(direction, checkedSort));
        Page<UserEntity> userEntities = userRepository.findAll(pageable);

        return userMapper.userListToDto(userEntities);
    }

    @Override
    public UserDto getUserById(Long id) {
        System.out.println(id);
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new GlobalNotFoundException("Unauthorized"));
        System.out.println(userEntity);
        return userMapper.userToDto(userEntity);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
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
    public RoleDto updateUserRole(RoleDto.RoleEnum roleDto, Long id) {
        UserEntity userEntity = getUserEntityById(id);
        RoleEntity role = roleRepository.findByName(RoleEntity.RoleEnum.valueOf(roleDto.name())).orElseThrow(() -> new GlobalNotFoundException("There is no such role: " + roleDto.name()));
        userEntity.setRole(role);
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
        return userMapper.userToDto(userRepository.findByName(name).orElseThrow(() -> new GlobalValidationException("Validation exception")));

    }

    @Override
    public UserDto getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
//        SecurityUser securityUser = SecurityUser.fromPrincipal(principal);
//        String username = securityUser.getUsername();
        return userMapper.userToDto(userRepository.findByName(username).orElseThrow(() -> new GlobalValidationException("User is not authorized!")));
    }

    private UserEntity getUserEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new GlobalNotFoundException("User not found"));
    }
}
