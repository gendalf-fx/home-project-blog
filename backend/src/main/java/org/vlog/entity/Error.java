package org.vlog.entity;

import lombok.Data;

@Data
// TODO: HANDLER EXCEPTION SPRING
public class Error {
    private String code;
    private String message;
}

