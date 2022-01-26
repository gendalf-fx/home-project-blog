package org.vlog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    @NonNull
    private UserDto user;
    @NonNull
    private String text;
    private String createdOn;
    private String updatedOn;
}
