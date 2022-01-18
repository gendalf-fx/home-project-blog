package org.vlog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Tag {
    @Id
    private Integer id;
    private String name;
}
