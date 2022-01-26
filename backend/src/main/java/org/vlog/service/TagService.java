package org.vlog.service;

import org.vlog.dto.TagDto;

import java.util.List;

public interface TagService {

    List<TagDto> getTags();

    TagDto getTagById(Long id);

    void removeTag(Long id);
}
