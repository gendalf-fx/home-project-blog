package org.vlog.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.vlog.entity.UserEntity;
import org.vlog.exception.custom.GlobalNotFoundException;
import org.vlog.exception.custom.GlobalValidationException;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@Data
public class SecurityUser implements UserDetails {

    private final String name;
    private final String password;
    private final Set<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserDetails fromUser(UserEntity userEntity) {
        return new User(
                userEntity.getName(),
                userEntity.getPassword(),
                userEntity.getRole().getName().getAuthorities()
        );
    }

    public static <T> SecurityUser fromPrincipal(T principal) {
        if (!(principal instanceof UserDetails)) {
            throw new GlobalNotFoundException("There isn`t instance of UserDetails");
        }
        return (SecurityUser) principal;

    }
}