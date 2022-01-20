package org.vlog.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vlog.service.UserService;

@Data
@Controller
@RequestMapping(path = "/users")
public class UserController {

    public final UserService userService;

}
