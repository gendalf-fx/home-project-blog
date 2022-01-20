package org.vlog.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.vlog.enums.Role;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
public class AccountEntity extends BaseEntity{
    @NonNull
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "`password`", nullable = false)
    private String password;


    @ManyToOne
    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private Role role = Role.BLOGGER;

    @OneToOne(targetEntity = UserEntity.class)
    private UserEntity user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccountEntity that = (AccountEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
