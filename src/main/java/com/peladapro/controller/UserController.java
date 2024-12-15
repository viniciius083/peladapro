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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createUser(@ModelAttribute UserDTO userDTO, Model model) {
        userService.createUser(userDTO);
        model.addAttribute("message", "Usuário criado com sucesso!");
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "newUser";
    }
}
