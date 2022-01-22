package com.example.gamelibrary.game;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class GameRequest {

    @NotBlank
    private final String title;

    @NotBlank
    private final String console;

    @Min(1)
    private final int players;

    public GameRequest(final String title, final String console, final int players) {
        this.title = title;
        this.console = console;
        this.players = players;
    }

    public Game toModel() {
        return new Game(title, console, players);
    }
}
