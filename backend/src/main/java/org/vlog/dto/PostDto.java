package org.vlog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;
    private Set<TagDto> tags;
    private LocalDateTime createdOn;
    @NonNull
    private UserDto user;
    @NonNull
    private String text;
    @NonNull
    private String title;
    @NonNull
    private String previewAttachment;
    private LocalDateTime updatedOn;
}
