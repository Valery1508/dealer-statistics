package ru.leverx.dealerStatistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.leverx.dealerStatistics.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
