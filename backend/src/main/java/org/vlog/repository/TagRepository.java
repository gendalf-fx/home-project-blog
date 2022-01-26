package org.vlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vlog.entity.TagEntity;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
}
