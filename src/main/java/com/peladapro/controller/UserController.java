package com.peladapro.controller;

import com.peladapro.dto.user.UserDTO;
import com.peladapro.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Mostra a tela de cadastro de usuários.
     *
     * @param model Model
     * @return Tela de cadastro de usuários
     */
    @GetMapping("/users/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "newUser";
    }

    /**
     * Cria um novo usuário.
     *
     * @param userDTO Dados do usuário
     * @param model Model
     * @return redireciona para a tela inicial
     */
    @PostMapping("/users/create")
    public String createUser(@ModelAttribute UserDTO userDTO, Model model) {
        userService.createUser(userDTO);
        model.addAttribute("message", "Usuário criado com sucesso!");
        return "redirect:/";
    }

    /**
     * Realiza o logout do usuário.
     *
     * @return Redireciona para a tela de login
     */
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }

    /**
     * Realiza o login do usuário.
     *
     * @param error se o login falhar, exibe uma mensagem
     * @param logout se o logout ocorrer, exibe uma mensagem
     * @param model Model
     * @return Tela de login
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuário ou senha inválidos!");
        }
        if (logout != null) {
            model.addAttribute("message", "Logout realizado com sucesso!");
        }
        return "login";
    }

}
