package org.vlog.service;

import org.vlog.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getComments(Long id, String name, String sort, Integer page_num, Integer page_size);

    CommentDto createComment(CommentDto commentDto, Long postId);

    CommentDto getCommentById(Long id);

    CommentDto updateComment(Long id, CommentDto commentDto);

    void removeComment(Long id);

    List<CommentDto> getCommentsByCurrentUser(String sort, Integer page_num, Integer page_size);

    CommentDto getCommentByIdAndCurrentUser(Long id);

    CommentDto updateCommentByIdAndCurrentUser(Long id, CommentDto commentDto);

    void removeCommentByIdAndCurrentUser(Long id);
}
