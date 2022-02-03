package org.vlog.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.vlog.dto.PostDto;
import org.vlog.service.PostService;

import java.util.List;

@Data
@RestController
@RequestMapping("posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PostDto createPost(@RequestBody PostDto postDto) {
        return postService.createPost(postDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<PostDto> getAllPosts(@RequestParam(required = false) Long id, @RequestParam(required = false) Long tag_id, @RequestParam(required = false) String tag_name,
                                     @RequestParam(required = false) String author_name,
                                     @RequestParam(defaultValue = "-id") String sort,
                                     @RequestParam(defaultValue = "0") Integer page_num,
                                     @RequestParam(defaultValue = "10") Integer page_size) {
        return postService.getPosts(id, tag_id, tag_name, author_name, sort, page_num, page_size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public PostDto getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}")
    public PostDto updatePostById(@PathVariable Long id, @RequestBody PostDto postDto) {
        return postService.updatePost(postDto, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void removePostById(@PathVariable Long id) {
        postService.removePost(id);
    }
}
