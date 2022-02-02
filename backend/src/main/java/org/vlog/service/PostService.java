package org.vlog.service;

import org.vlog.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getPosts(Long id, Long tag_id, String tag_name,
                           String author_name, String sort, Integer page_num,
                           Integer page_size);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    void removePost(Long id);
}
