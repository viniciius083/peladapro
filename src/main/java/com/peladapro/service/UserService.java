package com.peladapro.service;

import com.peladapro.dto.user.UserDTO;
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
     * 
     * @param userDTO Dados do usuário a ser criado.
     */
    public void createUser(UserDTO userDTO) {
        UserCommon user = new UserCommon();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encripta a senha
        user.setRole(userDTO.getRole());
        userRepository.save(user);
    }

    /**
     * Atualiza os dados de um usuário existente.
     * 
     * @param id      ID do usuário a ser atualizado.
     * @param userDTO Dados atualizados.
     */
    public void updateUser(Long id, UserDTO userDTO) {
        Optional<UserCommon> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            UserCommon user = existingUser.get();
            user.setUsername(userDTO.getUsername());
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }
            user.setRole(userDTO.getRole());
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuário com ID " + id + " não encontrado.");
        }
    }

    /**
     * Busca um usuário pelo ID.
     * 
     * @param id ID do usuário.
     * @return Usuário encontrado.
     */
    public UserCommon getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + id + " não encontrado."));
    }

    /**
     * Remove um usuário pelo ID.
     * 
     * @param id ID do usuário a ser removido.
     */
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário com ID " + id + " não encontrado.");
        }
    }

    /**
     * Busca um usuário pelo nome de usuário.
     * 
     * @param username Nome de usuário.
     * @return Usuário encontrado.
     */
    public UserCommon findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário com nome de usuário " + username + " não encontrado."));
    }
}
