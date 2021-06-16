package org.scd.controller;

import org.scd.config.exception.BusinessException;
import org.scd.model.User;
import org.scd.model.dto.LoginCredentialsDTO;
import org.scd.model.dto.RegisterCredentialsDTO;
import org.scd.model.security.CustomUserDetails;
import org.scd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping(path = "")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping(path = "/me")
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.ok(((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());
    }

    @PostMapping(path = "/login")
    public ResponseEntity<User> doLogin(@RequestBody final LoginCredentialsDTO loginCredentialsDTO) throws BusinessException {
        return ResponseEntity.ok(userService.doLogin(loginCredentialsDTO));
    }
    @PostMapping(path = "/register")
    public ResponseEntity<User> doRegister(@RequestBody final RegisterCredentialsDTO registerCredentialsDTO) throws BusinessException {
        return ResponseEntity.ok(userService.doRegister(registerCredentialsDTO));
    }
}
