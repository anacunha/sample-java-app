package com.example.gamelibrary.game;

import javax.annotation.processing.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String console;

    private int players;

    @Deprecated
    public Game() {
    }

    public Game(final String title, final String console, final int players) {
        this.title = title;
        this.console = console;
        this.players = players;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getConsole() {
        return console;
    }

    public int getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", console='" + console + '\'' +
                ", players=" + players +
                '}';
    }
}
