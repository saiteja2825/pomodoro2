package com.pomodoro2.app.Controllers;

import com.pomodoro2.app.Service.AuthService;
import com.pomodoro2.app.dtos.LoginRequest;
import com.pomodoro2.app.dtos.LoginResponse;
import com.pomodoro2.app.entity.users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody users user) {

        String token = authService.register(user);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest) {

        LoginResponse  response=authService.login(loginRequest);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<users> getCurrentUser() {

        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
