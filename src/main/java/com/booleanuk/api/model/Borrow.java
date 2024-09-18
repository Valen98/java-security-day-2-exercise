package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "borrows")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonIgnoreProperties(value = {"borrows"})
    private Game game;

    @Column(name = "isBorrowed")
    private Boolean isBorrowed;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "returnedDate")
    private Date returnedDate;

    public Borrow(User user, Game game) {
        this.user = user;
        this.game = game;
        this.isBorrowed = true;
        this.startDate = new Date();
    }

    public void returnGame(User user, Game game) {
        this.isBorrowed = false;
        this.returnedDate = new Date();
    }
}
