package org.vlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vlog.entity.RoleEntity;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(RoleEntity.RoleEnum name);
}
