package org.vlog.mapper;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.vlog.dto.TagDto;
import org.vlog.entity.TagEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto tagToDto(TagEntity tagEntity);

    TagEntity tagToEntity(TagDto tagDto);

    List<TagEntity> tagToEntityList(Page<TagEntity> tagsPage);

    List<TagDto> tagToDtoList(List<TagEntity> tagsEntity);
}
