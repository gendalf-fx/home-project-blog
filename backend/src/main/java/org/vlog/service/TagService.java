package org.vlog.service;

import org.vlog.dto.TagDto;

import java.util.List;

public interface TagService {

    List<TagDto> getTags(Long id, String name, String sort, Integer page_num, Integer page_size);

    TagDto getTagById(Long id);

    void removeTag(Long id);
}
