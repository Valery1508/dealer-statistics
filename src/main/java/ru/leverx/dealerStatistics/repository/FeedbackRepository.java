package ru.leverx.dealerStatistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.leverx.dealerStatistics.entity.Feedback;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query(value = "SELECT AVG(f.rating) FROM Feedback f WHERE f.user.id = :userId")
    Double findRatingByUserId(@Param("userId") Long userId);

    List<Feedback> findAllByUserId(Long userId);
}
