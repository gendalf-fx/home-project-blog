package org.vlog.enums;

import javax.persistence.*;

@Entity
@Table(name = "role", schema = "vlog")
public enum Role {
    BLOGGER, MODERATOR, ADMIN;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
