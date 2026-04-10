package com.pomodoro2.app.ServiceImpl;

import com.pomodoro2.app.Repositories.userRepository;
import com.pomodoro2.app.Security.JwtService;
import com.pomodoro2.app.Service.AuthService;
import com.pomodoro2.app.dtos.LoginRequest;
import com.pomodoro2.app.dtos.LoginResponse;
import com.pomodoro2.app.entity.users;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private userRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;


    public AuthServiceImpl(userRepository userRepo,PasswordEncoder passwordEncoder,JwtService jwtService){
        this.jwtService=jwtService;
        this.passwordEncoder=passwordEncoder;
        this.userRepo=userRepo;
    }

    @Override
    public String register(users user) {

        if(userRepo.findByUsername(user.getUsername()).isPresent()){
            throw new RuntimeException("username already exists");
        }

        user.setPassword((passwordEncoder.encode(user.getPassword())));
        userRepo.save(user);

        return jwtService.generateToken(user.getUsername());
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        users user=userRepo.findByUsername(loginRequest.getUsername()).orElseThrow(()->new RuntimeException("User not found"));

        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid Password");
        }
        String token= jwtService.generateToken(user.getUsername());

        return new LoginResponse(token);
    }

    @Override
    public users getCurrentUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        String username=authentication.getName();
        return userRepo.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
    }

    @Override
    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }
}
