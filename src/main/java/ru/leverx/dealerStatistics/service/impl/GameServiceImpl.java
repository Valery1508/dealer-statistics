package ru.leverx.dealerStatistics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.leverx.dealerStatistics.dto.GameDto;
import ru.leverx.dealerStatistics.entity.Game;
import ru.leverx.dealerStatistics.exception.EntityNotFoundException;
import ru.leverx.dealerStatistics.mapper.GameMapper;
import ru.leverx.dealerStatistics.repository.GameRepository;
import ru.leverx.dealerStatistics.service.GameService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameMapper gameMapper;

    @Override
    public GameDto create(GameDto gameDto) {
        Game game = gameMapper.toEntity(gameDto);
        Game saved = gameRepository.save(game);
        return gameMapper.toDto(saved);
    }

    @Override
    public GameDto get(Long id) {
        return gameRepository.findById(id).map(gameMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Game with id = " + id + " doesn't exist!"));
    }

    @Override
    public List<GameDto> getGames() {
        return listToDto((gameRepository.findAll()));
    }

    public List<GameDto> listToDto(List<Game> games) {
        return games.stream().map(gameMapper::toDto).collect(Collectors.toList());
    }
}
