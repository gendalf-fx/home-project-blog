package org.vlog.entity;

import lombok.Data;

@Data
public class Password {
    private String oldPassword;
    private String newPassword;
}
