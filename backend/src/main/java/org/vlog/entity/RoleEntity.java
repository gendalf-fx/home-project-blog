package org.vlog.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//@Entity
//@Table(name = "role")
public enum RoleEntity {
    BLOGGER, MODERATOR, ADMIN;
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
}
