package org.vlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vlog.entity.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
