package com.dhruv.evolve.service;

import com.dhruv.evolve.dto.AuthDTO;
import com.dhruv.evolve.dto.UserRequestDTO;
import com.dhruv.evolve.dto.UserResponseDTO;
import com.dhruv.evolve.entity.UserEntity;
import com.dhruv.evolve.exception.AuthenticationException;
import com.dhruv.evolve.exception.ConflictException;
import com.dhruv.evolve.exception.ResourceNotFoundException;
import com.dhruv.evolve.repository.UserRepository;
import com.dhruv.evolve.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserResponseDTO registerUser(UserRequestDTO userDTO) {

        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ConflictException("Email is already registered");
        }

        UserEntity newUser = UserEntity.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .passwordHash(passwordEncoder.encode(userDTO.getPassword()))
                .build();

        newUser = userRepository.save(newUser);

        return UserResponseDTO.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .createAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt())
                .build();
    }

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + authentication.getName()));


    }

    public UserResponseDTO getPublicUser(String email) {
        UserEntity currentUser = null;
        if(email == null) {
            currentUser = getCurrentUser();
        } else {
            currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        }

        return UserResponseDTO.builder()
                .id(currentUser.getId())
                .username(currentUser.getUsername())
                .email(currentUser.getEmail())
                .createAt(currentUser.getCreatedAt())
                .updatedAt(currentUser.getUpdatedAt())
                .build();
    }

    public Map<String, Object> authenticateAndGenerateToken(AuthDTO authDTO) {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
            String token = jwtUtil.generateToken(authDTO.getEmail());

            return Map.of(
                    "token", token,
                    "user", getPublicUser(authDTO.getEmail())
            );
        } catch (Exception e) {
            throw new AuthenticationException("Invalid email or password");
        }
    }



}
