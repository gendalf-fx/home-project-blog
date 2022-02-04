package org.vlog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.vlog.dto.CommentDto;
import org.vlog.entity.CommentEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto commentToDto(CommentEntity commentEntity);

//    @Mapping(target = "postDto", source = "commentDto.postDto", ignore = false)
    CommentEntity commentToEntity(CommentDto commentDto);

    List<CommentEntity> toCommentEntityList(Page<CommentEntity> commentEntityPage);

    List<CommentDto> toCommentDtoList(List<CommentEntity> commentEntities);


    @Mapping(target = "id", source = "oldComment.id")
    @Mapping(target = "user", source = "oldComment.user")
    @Mapping(target = "text", source = "newComment.text")
    @Mapping(target = "createdOn", source = "oldComment.createdOn")
    @Mapping(target = "updatedOn", source = "newComment.updatedOn")
    @Mapping(target = "post", source = "oldComment.post")
    CommentDto updateCommentDto(CommentDto oldComment, CommentDto newComment);
}
