package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "developer")
    private String developer;

    @Column(name = "releaseYear")
    private Integer releaseYear;

    @Column(name = "ageRating")
    private Integer ageRating;

    @Column(name = "numberOfPlayers")
    private Integer numberOfPlayers;

    @Column(name = "earlyAccess")
    private Boolean isEarlyAccess;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIncludeProperties(value = {"username"})
    private User user;


    public Game(String title, String genre, String publisher, String developer, Integer releaseYear, Integer ageRating, Integer numberOfPlayers, Boolean isEarlyAccess) {
        this.title = title;
        this.genre = genre;
        this.publisher = publisher;
        this.developer = developer;
        this.releaseYear = releaseYear;
        this.ageRating = ageRating;
        this.numberOfPlayers = numberOfPlayers;
        this.isEarlyAccess = isEarlyAccess;
    }
}
