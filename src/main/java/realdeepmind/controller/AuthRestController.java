package realdeepmind.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import realdeepmind.dto.LoginDto;
import realdeepmind.dto.user.UserRegisterDto;
import realdeepmind.entity.User;
import realdeepmind.mapper.UserMapper;
import realdeepmind.security.JwtUtils;
import realdeepmind.service.user.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDto registerDto) {
        User user = userMapper.toEntity(registerDto);
        userService.signUp(user);
        return ResponseEntity.ok("User registered successfully! Please wait for admin approval.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getUsername());

        String token = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(token);
    }
}