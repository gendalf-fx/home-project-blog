package org.vlog.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "`post`")
public class PostEntity extends BaseEntity {
    @ToString.Exclude
    @ManyToMany(targetEntity = TagEntity.class, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "id")
    )
    private Set<TagEntity> tags;

    private LocalDateTime createdOn;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NonNull
    @Column(nullable = false, name = "text")
    private String text;

    @NonNull
    @Column(nullable = false, name = "title")
    private String title;

    @NonNull
    @Column(nullable = false, name = "preview_attachment")
    private String previewAttachment;

    @Column(nullable = false, name = "updated_on")
    private LocalDateTime updatedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PostEntity that = (PostEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
