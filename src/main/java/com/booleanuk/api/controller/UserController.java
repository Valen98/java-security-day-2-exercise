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

import java.util.ArrayList;
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
        oldUser.setGames(oldUser.getGames());
        this.repository.save(oldUser);
        return ResponseEntity.ok(oldUser);
    }

    @PostMapping("{userId}/games/{gameId}")
    public ResponseEntity<?> borrowGame(@PathVariable(name = "userId") Integer userId, @PathVariable(name = "gameId") Integer gameId) {
        User user = this.repository.findById(userId).orElse(null);
        Game game = this.gameRepository.findById(gameId).orElse(null);

        if(game == null || user == null){
            return ResponseEntity.badRequest().body(user);
        }

        List<Game> gameList = user.getGames();
        gameList.add(game);
        user.setGames(gameList);
        game.setUser(user);

        return ResponseEntity.ok(this.repository.save(user));

    }
}