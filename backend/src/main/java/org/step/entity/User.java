package org.step.entity;

import lombok.Data;
import org.step.enums.Role;
@Data
public class User {
    private Integer id;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
