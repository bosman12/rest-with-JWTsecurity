package com.example.rest_example.restController;

import com.example.rest_example.dto.AuthenticationRequestDto;
import com.example.rest_example.dto.AuthenticationResponseDto;
import com.example.rest_example.dto.MessageResponse;
import com.example.rest_example.dto.RegistrationRequestDto;
import com.example.rest_example.entity.Role;
import com.example.rest_example.entity.User;
import com.example.rest_example.entity.enums.ERole;
import com.example.rest_example.repositrory.RoleRepository;
import com.example.rest_example.repositrory.UserRepository;
import com.example.rest_example.security.UserDetailsImpl;
import com.example.rest_example.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/auth/")
public class AuthenticationController {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationController(JwtUtils jwtUtils, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("signin")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequestDto authenticationRequestDto){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authenticationRequestDto.getUsername(),
                        authenticationRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new AuthenticationResponseDto(jwtToken, userDetails.getId(),
                userDetails.getUsername()));
    }

    @PostMapping("signup")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequestDto registrationRequestDto) {
        if (userRepository.existsByUserName(registrationRequestDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        } else if (userRepository.existsByEmail(registrationRequestDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error:  Email is exist"));
        }

        User user = new User(registrationRequestDto.getUsername(),
                registrationRequestDto.getEmail(),
                passwordEncoder.encode(registrationRequestDto.getPassword()));

        Set<String> reqRoles = registrationRequestDto.getRoles();
        Set<Role> roles = new HashSet<>();

        if (reqRoles == null) {
            Role userRole = roleRepository
                    .findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(userRole);
        }

        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }
}
