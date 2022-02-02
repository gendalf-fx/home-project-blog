package org.vlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vlog.entity.TagEntity;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    Optional<TagEntity> findTagEntityByName(String name);

    Optional<TagEntity> findTagEntityByIdAndName(Long id, String name);

}
