package org.vlog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;
    private Set<TagDto> tags;
    private String createdOn;
    @NonNull
    private UserDto user;
    @NonNull
    private String text;
    @NonNull
    private String title;
    @NonNull
    private String previewAttachment;
    private String updatedOn;
}
