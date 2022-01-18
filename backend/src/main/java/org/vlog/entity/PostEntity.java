package org.vlog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "post", schema = "vlog")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToMany()
    private List<Tag> tags;
    private String createdOn;
    @ManyToOne
    @JoinTable(name = "authors_posts",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    private AuthorEntity author;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String previewAttachment;
    private String updatedOn;
}
