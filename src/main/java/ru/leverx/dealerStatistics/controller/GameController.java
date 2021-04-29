package ru.leverx.dealerStatistics.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.leverx.dealerStatistics.dto.GameDto;
import ru.leverx.dealerStatistics.service.GameService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<GameDto> getGameById(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.get(id));
    }

    @PostMapping
    public ResponseEntity<GameDto> create(@Valid @RequestBody GameDto gameDto) {
        return ResponseEntity.ok(gameService.create(gameDto));
    }

    @GetMapping
    public List<GameDto> getGames() {
        return gameService.getGames();
    }
}
