package org.vlog.mapper;

import org.mapstruct.Mapper;
import org.vlog.dto.CommentDto;
import org.vlog.entity.CommentEntity;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto commentToDto(CommentEntity commentEntity);

    CommentEntity commentToEntity(CommentDto commentDto);
}
