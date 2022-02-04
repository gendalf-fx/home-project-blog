package org.vlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vlog.entity.CommentEntity;
import org.vlog.entity.TagEntity;
import org.vlog.entity.UserEntity;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Optional<CommentEntity> findCommentEntityByIdAndUser(Long id, UserEntity userEntity);

    Optional<CommentEntity> findCommentEntityByUser(UserEntity userEntity);
}
