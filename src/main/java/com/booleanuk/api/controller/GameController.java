package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("games")
public class GameController {

    @Autowired
    GameRepository repository;

    @GetMapping
    public ResponseEntity<List<Game>> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Game> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.repository.findById(id).orElse(null));
    }

    @PostMapping()
    public ResponseEntity<Game> create(@RequestBody Game game) {
        this.repository.save(game);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Game> delete(@PathVariable Integer id) {
        Game deleteGame = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        this.repository.delete(deleteGame);
        return ResponseEntity.ok(deleteGame);
    }

    @PutMapping("{id}")
    public ResponseEntity<Game> update(@PathVariable Integer id, Game updateGame) {
        Game oldGame = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));

        oldGame.setTitle(updateGame.getTitle());
        oldGame.setPublisher(updateGame.getPublisher());
        oldGame.setReleaseYear(updateGame.getReleaseYear());
        oldGame.setGenre(updateGame.getGenre());
        oldGame.setAgeRating(updateGame.getAgeRating());
        oldGame.setNumberOfPlayers(updateGame.getNumberOfPlayers());
        oldGame.setIsEarlyAccess(updateGame.getIsEarlyAccess());

        return ResponseEntity.ok(this.repository.save(oldGame));
    }
}
