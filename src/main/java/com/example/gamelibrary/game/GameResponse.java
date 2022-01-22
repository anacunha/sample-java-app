package com.example.gamelibrary.game;

public class GameResponse {

    private final Long id;
    private final String title;
    private final String console;
    private final int players;

    public GameResponse(final Game game) {
        this.id = game.getId();
        this.title = game.getTitle();
        this.console = game.getConsole();
        this.players = game.getPlayers();
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
}
