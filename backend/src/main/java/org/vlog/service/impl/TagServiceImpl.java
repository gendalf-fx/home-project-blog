package org.vlog.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vlog.dto.TagDto;
import org.vlog.exception.custom.GlobalNotFoundException;
import org.vlog.mapper.TagMapper;
import org.vlog.project.util.ProjectUtils;
import org.vlog.repository.TagRepository;
import org.vlog.service.TagService;

import java.util.List;
@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public List<TagDto> getTags(Long id, String name, String sort, Integer page_num, Integer page_size) {
        if (id != null && name != null) {
            return List.of(tagMapper.tagToDto(tagRepository.findTagEntityByIdAndName(id, name).orElseThrow(
                    () -> new GlobalNotFoundException("Tag with id: " + id + "and name: " + name + " doesn`t exist!")
            )));
        } else if (id != null) {
            return List.of(getTagById(id));
        } else if (name != null) {
            return List.of(tagMapper.tagToDto(tagRepository.findTagEntityByName(name).orElseThrow(
                    () -> new GlobalNotFoundException("Tag with name: " + name + " doesn`t exist!")
            )));
        } else {
            Pageable pagination = ProjectUtils.pagination(sort, page_num, page_size);
            return tagMapper.tagToDtoList(tagMapper.tagToEntityList(tagRepository.findAll(pagination)));
        }
    }

    @Override
    public TagDto getTagById(Long id) {
        return tagMapper.tagToDto(tagRepository.findById(id).orElseThrow(
                () -> new GlobalNotFoundException("Tag with id: " + id + " doesn`t exist!")
        ));
    }

    @Override
    public void removeTag(Long id) {
        tagRepository.delete(tagRepository.findById(id).orElseThrow(
                () -> new GlobalNotFoundException("Tag with id: " + id + " doesn`t exist!")
        ));
    }
}
