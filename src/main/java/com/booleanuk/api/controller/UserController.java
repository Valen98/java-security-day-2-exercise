package com.booleanuk.api.controller;


import com.booleanuk.api.dto.UserDTO;
import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserRepository repository;

    @Autowired
    GameRepository gameRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> delete(@PathVariable Integer id) {
        User deletUser = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        this.repository.delete(deletUser);

        return ResponseEntity.ok(deletUser);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody UserDTO updateUser) {
        User oldUser = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        oldUser.setUsername(updateUser.getUsername());
        oldUser.setEmail(updateUser.getEmail());
        oldUser.setPassword(updateUser.getPassword());
        oldUser.setRoles(updateUser.getRoles());
        oldUser.setBorrows(oldUser.getBorrows());
        this.repository.save(oldUser);
        return ResponseEntity.ok(oldUser);
    }


}