package org.vlog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    @NonNull
    private UserDto user;
    @NonNull
    private String text;
    @NonNull
    private PostDto post;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
