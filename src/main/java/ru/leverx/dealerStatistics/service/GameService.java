package ru.leverx.dealerStatistics.service;

import org.springframework.stereotype.Service;
import ru.leverx.dealerStatistics.dto.GameDto;

import java.util.List;

@Service
public interface GameService {
    GameDto create(GameDto gameDto);

    GameDto get(Long id);

    List<GameDto> getGames();

    GameDto change(GameDto gameDto, Long id);

    List<GameDto> delete(Long id);

    List<GameDto> getGamessByUserId(Long userId);
}
