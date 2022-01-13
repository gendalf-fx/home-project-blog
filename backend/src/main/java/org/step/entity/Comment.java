package org.step.entity;

import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private Author author;
    private String text;
    private String createdOn;
    private String updatedOn;
}
