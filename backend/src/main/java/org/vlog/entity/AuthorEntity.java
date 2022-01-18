package org.vlog.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "author", schema = "vlog")
public class AuthorEntity {
    @Id
    @Column(nullable = false)
    private String name;
    private String firstName;
    private String lastName;

}
