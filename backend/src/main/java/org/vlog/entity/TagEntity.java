package org.vlog.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tag")
public class TagEntity extends BaseEntity {
    @NonNull
    @Column(nullable = false, name = "name", unique = true)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TagEntity tag = (TagEntity) o;
        return  Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
