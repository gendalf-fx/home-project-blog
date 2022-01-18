package org.vlog.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    // TODO : FIX ME
    @JoinTable(name = "author", joinColumns = {"id"})
    private AuthorEntity author;
    private String text;
    private String createdOn;
    private String updatedOn;
}
