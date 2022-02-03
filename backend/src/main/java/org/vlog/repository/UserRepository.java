package org.vlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vlog.dto.PostDto;
import org.vlog.dto.RoleDto;
import org.vlog.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByName(String name);
    Optional<UserEntity> findUserEntityByIdAndName(Long id, String name);

//    @Query("select * from user where user.name = @name and ( @id is null or user.id = @id )")

}
