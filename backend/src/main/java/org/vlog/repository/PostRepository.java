package org.vlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vlog.dto.PostDto;
import org.vlog.entity.PostEntity;
import org.vlog.entity.TagEntity;
import org.vlog.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<List<PostEntity>> findPostEntitiesByIdAndTagsAndUser(Long id, TagEntity tagEntity, UserEntity userEntity);

    Optional<List<PostEntity>> findPostEntitiesByTagsAndUser(TagEntity tagEntity, UserEntity userEntity);

    Optional<List<PostEntity>> findPostEntitiesByIdAndUser(Long id, UserEntity userEntity);

    Optional<List<PostEntity>> findPostEntitiesByUser(UserEntity userEntity);

    Optional<List<PostEntity>> findPostEntitiesByIdAndTags(Long id, TagEntity tagEntity);

    Optional<List<PostEntity>> findPostEntitiesByTags(TagEntity tagEntity);


}
