package org.vlog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.vlog.dto.PostDto;
import org.vlog.entity.PostEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public
interface PostMapper {

    PostDto toPostDto(PostEntity postEntity);

    PostEntity toPostEntity(PostDto postDto);

    @Mapping(target = "id", source = "oldPost.id")
    @Mapping(target = "createdOn", source = "oldPost.createdOn")
    @Mapping(target = "tags", source = "newPost.tags")
    @Mapping(target = "user", source = "oldPost.user")
    @Mapping(target = "text", source = "newPost.text")
    @Mapping(target = "title", source = "newPost.title")
    @Mapping(target = "previewAttachment", source = "newPost.previewAttachment")
    @Mapping(target = "updatedOn", source = "newPost.updatedOn")
    PostEntity updatePost(PostDto oldPost, PostDto newPost);

    List<PostEntity> toList(Page<PostEntity> postEntity);

    List<PostDto> toPostDtoList(List<PostEntity> postEntities);
}