package org.vlog.service;

import org.vlog.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getComments(Long postId);

    CommentDto createComment(CommentDto commentDto, Long postId);

    CommentDto getCommentById(Long postId, Long id);

    CommentDto updateComment(Long postId, Long id);

    void removeComment(Long postId, Long id);
}
