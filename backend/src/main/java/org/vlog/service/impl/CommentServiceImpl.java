package org.vlog.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vlog.dto.CommentDto;
import org.vlog.dto.PostDto;
import org.vlog.dto.UserDto;
import org.vlog.exception.custom.GlobalNotFoundException;
import org.vlog.mapper.CommentMapper;
import org.vlog.project.util.ProjectUtils;
import org.vlog.repository.CommentRepository;
import org.vlog.repository.UserRepository;
import org.vlog.service.CommentService;
import org.vlog.service.PostService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostService postService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;


    @Override
    public List<CommentDto> getComments(Long id, String name, String sort, Integer page_num, Integer page_size) {
        if (id != null && name != null) {
            return List.of(commentMapper.
                    commentToDto(commentRepository
                            .findCommentEntityByIdAndUser(id,
                                    userRepository.findByName(name).orElseThrow(
                                            () -> new GlobalNotFoundException("User with name: " + name + " doesn`t exist!")
                                    )).orElseThrow(
                                    () -> new GlobalNotFoundException("Comment with id: " + id + "and username: " + name + " doesn`t exist!")
                            )));
        } else if (id != null) {
            return List.of(commentMapper
                    .commentToDto(commentRepository
                            .findById(id).orElseThrow(
                                    () -> new GlobalNotFoundException("Comment with id: " + id + " doesn`t exist!"))));
        } else if (name != null) {
            return List.of(commentMapper.commentToDto(
                    commentRepository.findCommentEntityByUser(
                            userRepository.findByName(name).orElseThrow(
                                    () -> new GlobalNotFoundException("User with name: " + name + " doesn`t exist!")
                            )
                    ).orElseThrow(
                            () -> new GlobalNotFoundException("Comment with id: " + id + "and username: " + name + " doesn`t exist!")
                    )
            ));
        } else {
            Pageable pagination = ProjectUtils.pagination(sort, page_num, page_size);
            return commentMapper.toCommentDtoList(commentMapper.toCommentEntityList(commentRepository.findAll(pagination)));
        }

    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        PostDto postById = postService.getPostById(postId);
        UserDto userDto = postById.getUser();
        commentDto.setCreatedOn(LocalDateTime.now());
        commentDto.setUpdatedOn(LocalDateTime.now());
        commentDto.setUser(userDto);
        commentDto.setPost(postById);
        return commentMapper.commentToDto(commentRepository.save(commentMapper.commentToEntity(commentDto)));
    }

    @Override
    public CommentDto getCommentById(Long id) {
        return commentMapper.commentToDto(commentRepository.findById(id).orElseThrow(
                () -> new GlobalNotFoundException("Comment with: " + id + " doesn`t exist!")
        ));
    }

    @Override
    public CommentDto updateComment(Long id, CommentDto commentDto) {
        CommentDto commentById = getCommentById(id);
        commentDto.setUpdatedOn(LocalDateTime.now());
        CommentDto updateCommentDto = commentMapper.updateCommentDto(commentById, commentDto);
        commentRepository.save(commentMapper.commentToEntity(updateCommentDto));
        return updateCommentDto;
    }

    @Override
    public void removeComment(Long id) {
        commentRepository.delete(commentRepository.findById(id).orElseThrow(
                () -> new GlobalNotFoundException("Comment with: " + id + " doesn`t exist!")
        ));
    }

    @Override
    public List<CommentDto> getCommentsByCurrentUser(String sort, Integer page_num, Integer page_size) {
        Pageable pagination = ProjectUtils.pagination(sort, page_num, page_size);
        return commentMapper.toCommentDtoList(commentMapper.toCommentEntityList(commentRepository.findAll(pagination)));

    }

    @Override
    public CommentDto getCommentByIdAndCurrentUser(Long id) {
        return getCommentById(id);
    }

    @Override
    public CommentDto updateCommentByIdAndCurrentUser(Long id, CommentDto commentDto) {
        return updateComment(id, commentDto);
    }

    @Override
    public void removeCommentByIdAndCurrentUser(Long id) {
        removeComment(id);
    }
}
