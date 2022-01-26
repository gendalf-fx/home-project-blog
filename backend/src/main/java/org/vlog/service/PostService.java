package org.vlog.service;

import org.vlog.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getPosts();

    PostDto getPostById();

    PostDto updatePost(PostDto postDto, Long id);

    void removePost(Long id);
}
