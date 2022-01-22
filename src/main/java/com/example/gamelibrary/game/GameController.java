package com.example.gamelibrary.game;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameRepository repository;

    public GameController(final GameRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public String create(@RequestBody @Valid GameRequest request) {
        Game game = request.toModel();
        repository.save(game);
        return game.toString();
    }

    @GetMapping
    public List<GameResponse> list() {
        return repository.findAll().stream()
                .map(GameResponse::new)
                .collect(Collectors.toList());
    }
}
