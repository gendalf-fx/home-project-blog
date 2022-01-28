package org.vlog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RoleDto {
    BLOGGER("blogger"), MODERATOR("moderator"), ADMIN("admin");
    @JsonProperty(required = true)
    private String name;

    RoleDto(String name) {
        this.name = name;
    }
}
