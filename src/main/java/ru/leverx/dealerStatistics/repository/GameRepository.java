package ru.leverx.dealerStatistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.leverx.dealerStatistics.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
