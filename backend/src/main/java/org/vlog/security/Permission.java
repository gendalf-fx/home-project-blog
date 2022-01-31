package org.vlog.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    BLOGGER_ACCESS("blogger"),
    MODERATOR_ACCESS("moderator"),
    ADMIN_ACCESS("admin");
    private final String permission;
}
