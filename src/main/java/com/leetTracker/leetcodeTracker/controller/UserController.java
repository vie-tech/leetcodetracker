package com.leetTracker.leetcodeTracker.controller;


import com.leetTracker.leetcodeTracker.dto.LoginUserRequest;
import com.leetTracker.leetcodeTracker.dto.RegisterUserRequest;
import com.leetTracker.leetcodeTracker.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest request, HttpServletResponse response){
        userService.registerUserAccount(request,response);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginUserRequest request, HttpServletResponse response){
       userService.loginUser(request, response);
        return ResponseEntity.ok(Map.of("success", true, "isAuthenticated", true));
    }
}
