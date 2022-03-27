package org.vlog.entity;


import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.vlog.security.Permission;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;


    @Getter
    @AllArgsConstructor
    public enum RoleEnum {
        BLOGGER(Set.of(Permission.BLOGGER_ACCESS)),
        MODERATOR(Set.of(Permission.BLOGGER_ACCESS, Permission.MODERATOR_ACCESS)),
        ADMIN(Set.of(Permission.BLOGGER_ACCESS, Permission.MODERATOR_ACCESS, Permission.ADMIN_ACCESS));

        private final Set<Permission> permissions;

        public Set<SimpleGrantedAuthority> getAuthorities() {
            return getPermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                    .collect(Collectors.toSet());
        }

    }
}
