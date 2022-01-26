package org.vlog.entity;

import javax.persistence.*;

@Entity
@Table(name = "role")
public enum RoleEntity {
    BLOGGER, MODERATOR, ADMIN;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
