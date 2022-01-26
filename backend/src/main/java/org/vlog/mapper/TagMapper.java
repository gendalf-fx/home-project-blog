package org.vlog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.vlog.dto.TagDto;
import org.vlog.entity.TagEntity;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto tagToDto(TagEntity tagEntity);

    TagEntity tagToEntity(TagDto tagDto);
}
