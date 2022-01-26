package org.vlog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.vlog.dto.PostDto;
import org.vlog.entity.PostEntity;

@Mapper(componentModel = "spring")
interface PostMapper {

    PostDto toPostDto(PostEntity postEntity);

    PostEntity toPostEntity(PostDto postDto);

}