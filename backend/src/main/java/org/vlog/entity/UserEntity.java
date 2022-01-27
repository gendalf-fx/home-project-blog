package org.vlog.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`user`")
public class UserEntity extends BaseEntity {

    @NonNull
    @Column(nullable = false, unique = true, name = "name")
    private String name;

    @NonNull
    @Column(nullable = false, unique = true, name = "email")
    private String email;

    @Column(name = "`password`", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToOne(cascade = CascadeType.ALL)
    @Enumerated(value = EnumType.STRING)
    @JoinColumn(name = "role_id")
    private RoleEntity role = RoleEntity.BLOGGER;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
