package com.peladapro.model;

import com.peladapro.enumeration.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tb_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
