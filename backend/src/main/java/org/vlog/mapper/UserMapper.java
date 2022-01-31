package org.vlog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.vlog.dto.UserDto;
import org.vlog.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity userToEntity(UserDto userDto);

    UserDto userToDto(UserEntity userEntity);

    List<UserDto> userListToDto(Page<UserEntity> userEntities);

    @Mapping(target="id", source = "oldUser.id")
    @Mapping(target="name", source = "updatedUser.name")
    @Mapping(target="firstName", source = "updatedUser.firstName")
    @Mapping(target="lastName", source = "updatedUser.lastName")
    @Mapping(target="email", source = "oldUser.email")
    @Mapping(target="password", source = "oldUser.password")
    @Mapping(target="role", source = "oldUser.role")
    UserEntity updateUser(UserDto oldUser, UserDto updatedUser);
}
