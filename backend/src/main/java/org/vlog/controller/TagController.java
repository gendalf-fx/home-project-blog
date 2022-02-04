package org.vlog.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.vlog.dto.TagDto;
import org.vlog.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagController {

    private final TagService tagService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TagDto> getAllTags(@RequestParam(required = false) Long id, @RequestParam(required = false) String name,
                                   @RequestParam(defaultValue = "-id") String sort,
                                   @RequestParam(defaultValue = "0") Integer page_num,
                                   @RequestParam(defaultValue = "10") Integer page_size) {
        return tagService.getTags(id, name, sort, page_num, page_size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public TagDto getTagById(@PathVariable Long id) {
        return tagService.getTagById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void removeTagById(@PathVariable Long id) {
        tagService.removeTag(id);
    }

}
