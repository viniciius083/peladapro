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

@Controller()
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "newUser";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/create")
    public String createUser(@ModelAttribute UserDTO userDTO, Model model) {
        userService.createUser(userDTO);
        model.addAttribute("message", "Usuário criado com sucesso!");
        return "redirect:/";
    }

}
