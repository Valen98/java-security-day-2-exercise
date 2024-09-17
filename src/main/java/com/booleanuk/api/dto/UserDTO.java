package com.booleanuk.api.dto;

import com.booleanuk.api.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private String email;
    private String username;
    private String password;
    private Set<Role> roles;
    private List<Integer> game;

}
