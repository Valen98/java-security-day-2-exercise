package com.booleanuk.api.controller;

import com.booleanuk.api.dto.BorrowDTO;
import com.booleanuk.api.model.Borrow;
import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.BorrowRepository;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("borrows")
public class BorrowController {

    @Autowired
    BorrowRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GameRepository gameRepository;


    @PostMapping()
    public ResponseEntity<Borrow> borrow(@RequestBody BorrowDTO borrowDTO) {
        User user = this.userRepository.findById(borrowDTO.getUser()).orElse(null);
        Game game = this.gameRepository.findById(borrowDTO.getGame()).orElse(null);

        if(game == null || user == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Borrow borrow = new Borrow(user, game);
        user.addGameToBorrow(borrow);
        game.addGameToBorrow(borrow);

        this.repository.save(borrow);
        this.gameRepository.save(game);
        this.userRepository.save(user);

        return ResponseEntity.ok(borrow);
    }

    @PostMapping("return/{id}")
    public ResponseEntity<Borrow> returnBorrow(@PathVariable Integer id) {
        Borrow borrow = this.repository.findById(id).orElse(null);
        if(borrow == null) {
            return ResponseEntity.badRequest().body(null);
        }

        User user = this.userRepository.findById(borrow.getUser().getId()).orElse(null);
        Game game = this.gameRepository.findById(borrow.getGame().getId()).orElse(null);

        if(game == null || user == null ) {
            return ResponseEntity.badRequest().body(null);
        }

        if(borrow.getIsBorrowed() && borrow.getUser() == user && borrow.getGame() == game) {
            borrow.returnGame(user, game);
            user.returnGameFromBorrow(borrow);
            game.returnGameFromBorrow(borrow);

            this.repository.save(borrow);
            this.userRepository.save(user);
            this.gameRepository.save(game);

            return ResponseEntity.ok(borrow);
        }
        return ResponseEntity.badRequest().body(null);
    }
}
