package ru.leverx.dealerStatistics.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.leverx.dealerStatistics.dto.GameDto;
import ru.leverx.dealerStatistics.entity.Game;
import ru.leverx.dealerStatistics.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameMapper {

    @Autowired
    private UserRepository userRepository;

    public Game toEntity(GameDto gameDto) {
        Game game = new Game();
        game.setId(gameDto.getId());
        game.setName(gameDto.getName());
        game.setDescription(gameDto.getDescription());
        game.setUser(userRepository.findById(gameDto.getUser_id()).get());
        return game;
    }

    public GameDto toDto(Game game) {
        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setName(game.getName());
        gameDto.setDescription(game.getDescription());
        gameDto.setUser_id(game.getUser().getId());
        return gameDto;
    }

    public List<GameDto> toDtos(List<Game> games) {
        return games.stream().map(this::toDto).collect(Collectors.toList());
    }

}
