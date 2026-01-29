package com.dhruv.evolve.service;

import com.dhruv.evolve.dto.UserRequestDTO;
import com.dhruv.evolve.dto.UserResponseDTO;
import com.dhruv.evolve.entity.User;
import com.dhruv.evolve.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDTO registerUser(UserRequestDTO userDTO) {

        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        User newUser = User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .passwordHash(userDTO.getPassword())
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

}
