package com.pomodoro2.app.Service;

import com.pomodoro2.app.dtos.LoginRequest;
import com.pomodoro2.app.dtos.LoginResponse;
import com.pomodoro2.app.entity.users;

public interface AuthService {

    String register(users user);

    LoginResponse login(LoginRequest loginRequest);

    users getCurrentUser();

    boolean validateToken(String token);
}
