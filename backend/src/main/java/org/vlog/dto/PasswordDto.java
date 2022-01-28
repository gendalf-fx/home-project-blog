package org.vlog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class PasswordDto {
    @NonNull
    private String oldPassword;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}$", message = "The password of the User. MUST contain a mix of upper and lower case letters,\n" +
            " as well as digits. Minimal length is 8 symbols")
    @NonNull
    private String newPassword;
}