package org.vlog.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vlog.dto.PostDto;
import org.vlog.entity.PostEntity;
import org.vlog.entity.TagEntity;
import org.vlog.entity.UserEntity;
import org.vlog.exception.custom.GlobalNotFoundException;
import org.vlog.mapper.PostMapper;
import org.vlog.mapper.TagMapper;
import org.vlog.mapper.UserMapper;
import org.vlog.project.util.ProjectUtils;
import org.vlog.repository.PostRepository;
import org.vlog.repository.TagRepository;
import org.vlog.repository.UserRepository;
import org.vlog.service.PostService;
import org.vlog.service.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserService userService;

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final TagMapper tagMapper;

    private final TagRepository tagRepository;

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        UserEntity userEntity = userMapper.userToEntity(userService.getCurrentUser());
        PostEntity postEntity = postMapper.toPostEntity(postDto);
        postEntity.setUser(userEntity);

        List<TagEntity> oldTags = tagRepository.findAll();
        Set<String> tagsName = oldTags.stream().map(TagEntity::getName).collect(Collectors.toSet());
        Set<TagEntity> newTags = postEntity.getTags().stream()
                .filter(tagEntity -> !tagsName.contains(tagEntity.getName()))
                .map(tagRepository::save).collect(Collectors.toSet());

        newTags.addAll(oldTags.stream().filter(tagEntity -> tagsName.contains(tagEntity.getName())).collect(Collectors.toSet()));
        postEntity.getTags().clear();
        postEntity.setTags(newTags);

        postEntity.setCreatedOn(LocalDateTime.now());
        postEntity.setUpdatedOn(LocalDateTime.now());
        postRepository.save(postEntity);
        return postMapper.toPostDto(postEntity);

    }

    /**
     * In case we have large amount of tags in the database
     *
     * @param postDto
     * @return PostDto instance
     */
    private PostDto createPostWithLargeAmountOfTags(PostDto postDto) {
        UserEntity userEntity = userMapper.userToEntity(userService.getCurrentUser());
        PostEntity postEntity = postMapper.toPostEntity(postDto);
        postEntity.setUser(userEntity);

        Set<TagEntity> newTags = postEntity.getTags().stream()
                .peek(tagEntity -> tagRepository.findTagEntityByName(tagEntity.getName()))
                .filter(tagEntity -> Optional.empty().isEmpty())
                .map(tagRepository::save)
                .collect(Collectors.toSet());

        Set<TagEntity> oldTags = postEntity.getTags().stream()
                .map(tagEntity -> tagRepository.findTagEntityByName(tagEntity.getName()))
                .filter(tagEntity -> Optional.empty().isPresent())
                .map(Optional::get)
                .collect(Collectors.toSet());

        newTags.addAll(oldTags);

        postEntity.getTags().clear();
        postEntity.setTags(newTags);

        postEntity.setCreatedOn(LocalDateTime.now());
        postEntity.setUpdatedOn(LocalDateTime.now());
        postRepository.save(postEntity);
        return postMapper.toPostDto(postEntity);
    }

    private final String POST_NOT_FOUND = "Post doesn`t exists!";

    @Override
    public List<PostDto> getPosts(Long id, Long tag_id, String tag_name,
                                  String author_name, String sort, Integer page_num,
                                  Integer page_size) {
        if (id != null && tag_id != null && tag_name != null && author_name != null) {
            return postMapper.toPostDtoList(postRepository.findPostEntitiesByIdAndTagsAndUser(id,
                            tagRepository.findTagEntityByIdAndName(tag_id, tag_name).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)),
                            userRepository.findByName(author_name).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)))
                    .orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)));

        } else if (id != null && tag_id != null && tag_name != null) {
            return postMapper.toPostDtoList(postRepository.findPostEntitiesByIdAndTags(id,
                            tagRepository.findTagEntityByIdAndName(tag_id, tag_name).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)))
                    .orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)));
        } else if (id != null && tag_id != null) {
            return postMapper.toPostDtoList(postRepository.findPostEntitiesByIdAndTags(id,
                            tagRepository.findById(tag_id).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)))
                    .orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)));

        } else if (id != null && tag_name != null) {
            return postMapper.toPostDtoList(postRepository.findPostEntitiesByIdAndTags(id,
                            tagRepository.findTagEntityByName(tag_name).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)))
                    .orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)));

        } else if (id != null && author_name != null) {
            return postMapper.toPostDtoList(postRepository.findPostEntitiesByIdAndUser(id,
                            userRepository.findByName(author_name).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)))
                    .orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)));

        } else if (tag_id != null && tag_name != null && author_name != null) {
            return postMapper.toPostDtoList(postRepository.findPostEntitiesByTagsAndUser(
                            tagRepository.findTagEntityByIdAndName(tag_id, tag_name).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)),
                            userRepository.findByName(author_name).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)))
                    .orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)));

        } else if (tag_id != null && author_name != null) {
            return postMapper.toPostDtoList(postRepository.findPostEntitiesByTagsAndUser(
                            tagRepository.findById(tag_id).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)),
                            userRepository.findByName(author_name).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)))
                    .orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)));

        } else if (tag_name != null && author_name != null) {
            return postMapper.toPostDtoList(postRepository.findPostEntitiesByTagsAndUser(
                            tagRepository.findTagEntityByName(tag_name).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)),
                            userRepository.findByName(author_name).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)))
                    .orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)));

        } else if (id != null) {
            return List.of(postMapper.toPostDto(postRepository.findById(id).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND))));

        } else if (tag_id != null) {
            return postMapper.toPostDtoList(postRepository.findPostEntitiesByTags(
                            tagRepository.findById(tag_id).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)))
                    .orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)));
        } else if (tag_name != null) {
            return postMapper.toPostDtoList(postRepository.findPostEntitiesByTags(
                            tagRepository.findTagEntityByName(tag_name).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)))
                    .orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)));
        } else if (author_name != null) {
            return postMapper.toPostDtoList(postRepository.findPostEntitiesByUser(
                            userRepository.findByName(author_name).orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)))
                    .orElseThrow(() -> new GlobalNotFoundException(POST_NOT_FOUND)));
        } else {

            Pageable pagination = ProjectUtils.pagination(sort, page_num, page_size);
            return postMapper.toPostDtoList(postMapper.toList(postRepository.findAll(pagination)));
        }
    }

    @Override
    public PostDto getPostById(Long id) {
        return postMapper.toPostDto(postRepository.findById(id).orElseThrow(() -> new GlobalNotFoundException("Post doesn`t exists!")));
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        PostDto oldPost = getPostById(id);
        PostEntity postEntity = postRepository.save(postMapper.updatePost(oldPost, postDto));
        return postMapper.toPostDto(postEntity);
    }

    @Override
    public void removePost(Long id) {
        postRepository.delete(postMapper.toPostEntity(getPostById(id)));
    }
}
