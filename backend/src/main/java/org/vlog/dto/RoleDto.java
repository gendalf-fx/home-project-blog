package org.vlog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class RoleDto {
    @JsonIgnore
    private Long id;
    @JsonProperty
    private RoleEnum name;

    @Getter
    @AllArgsConstructor
    public enum RoleEnum {
        BLOGGER, MODERATOR, ADMIN;
    }
}
