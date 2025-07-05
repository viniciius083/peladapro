package com.peladapro.service;

import com.peladapro.dto.user.UserDTO;
import com.peladapro.enumeration.Role;
import com.peladapro.model.UserCommon;
import com.peladapro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Cria um novo usuário.
     * @param userDTO  objeto contendo os dados do novo usuário
     */
    public void createUser(UserDTO userDTO) {
        UserCommon user = UserCommon.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }
}
