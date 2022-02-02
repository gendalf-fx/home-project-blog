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
import org.vlog.project.util.ProjectUtils;
import org.vlog.repository.RoleRepository;
import org.vlog.repository.UserRepository;
import org.vlog.service.UserService;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        roleRepository.saveAll(List.of(
                new RoleEntity(null, RoleEntity.RoleEnum.MODERATOR), new RoleEntity(null, RoleEntity.RoleEnum.BLOGGER)));
        userRepository.save(new UserEntity("admin", "admin", passwordEncoder.encode("admin"),
                "admin", "admin", new RoleEntity(null, RoleEntity.RoleEnum.ADMIN)));

    }

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = userMapper.userToEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(roleRepository.findByName(RoleEntity.RoleEnum.BLOGGER).orElseThrow(() ->
                new GlobalNotFoundException("This role doesn`t exists!")));
        userRepository.save(userEntity);
        return userMapper.userToDto(userEntity);
    }

    @Override
    public List<UserDto> getAllUsers(Long id, String name, String sort, Integer page_num, Integer page_size) {
        if (id != null && name != null) {
            return List.of(userMapper.userToDto(userRepository.findUserEntityByIdAndName(id, name).orElseThrow(() ->
                    new GlobalNotFoundException("User with id: " + id + " and name: " + name + " doesn`t exist!"))));
        } else if (id != null) {
            return List.of(getUserById(id));
        } else if (name != null) {
            return List.of(getUserByName(name));
        } else {
            Pageable pageable = ProjectUtils.pagination(sort, page_num, page_size);
            Page<UserEntity> userEntities = userRepository.findAll(pageable);

            return userMapper.userListToDto(userEntities);
        }
    }



    @Override
    public UserDto getUserById(Long id) {
        System.out.println(id);
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new GlobalNotFoundException("User with id: "
                + id + " doesn`t exists!"));
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
        userRepository.delete(userRepository.findById(id).orElseThrow(() -> new GlobalNotFoundException("Access denied, User with id: "
                + id + " doesn`t exists!")));
    }

    @Override
    public RoleDto getUserRoleById(Long id) {
        UserDto userDto = getUserById(id);
        if (userDto.getRole() != null) {
            return userDto.getRole();
        } else {
            throw new GlobalNotFoundException("Role with id:" + id + " doesn`t exist!");
        }
    }

    @Override
    public RoleDto updateUserRole(RoleDto.RoleEnum roleDto, Long id) {
        UserEntity userEntity = getUserEntityById(id);
        RoleEntity role = roleRepository.findByName(RoleEntity.RoleEnum.valueOf(roleDto.name())).orElseThrow(()
                -> new GlobalNotFoundException("Role with name :" + roleDto.name() + " doesn`t exist!"));
        userEntity.setRole(role);
        userRepository.save(userEntity);
        return userMapper.userToDto(userEntity).getRole();
    }


    @Override
    public UserDto updatePassword(PasswordDto passwordDto) {
        UserEntity userEntity = userMapper.userToEntity(getCurrentUser());
        userEntity.setPassword(passwordDto.getNewPassword());
        userRepository.save(userEntity);
        return userMapper.userToDto(userEntity);
    }

    public UserDto getUserByName(String name) {
        return userMapper.userToDto(userRepository.findByName(name).orElseThrow(() -> new GlobalValidationException
                ("User with name :" + name + " doesn`t exist!")));

    }

    @Override
    public UserDto getCurrentUser() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        return userMapper.userToDto(userRepository.findByName(username).orElseThrow(() -> new GlobalValidationException
                ("User is not authorized!")));
    }

    @Override
    public UserDto updateCurrentUser(UserDto userDto) {
        UserDto currentUser = getCurrentUser();
        UserEntity userEntity = userMapper.updateUser(currentUser, userDto);
        userRepository.save(userEntity);
        return userMapper.userToDto(userEntity);
    }

    private UserEntity getUserEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new GlobalNotFoundException("User doesn`t exist!"));
    }
}
