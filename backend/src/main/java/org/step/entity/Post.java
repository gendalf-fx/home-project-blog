package org.step.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Post {
    private Integer id;
    private Tag [] tags;
    private String createdOn;
    private Author author;
    private String text;
    private String title;
    private String previewAttachment;
    private String updatedOn;
}
