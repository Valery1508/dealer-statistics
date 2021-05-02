package ru.leverx.dealerStatistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.leverx.dealerStatistics.entity.User;
import ru.leverx.dealerStatistics.entity.UserRole;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u FROM User u WHERE u.userRole = :role")
    List<User> findByUserRole(@Param("role") UserRole role);

    @Query(value = "SELECT u FROM User u WHERE u.userRole = :role AND NOT u.rating = 0 ORDER BY u.rating DESC")
    List<User> findAllTreidersOrderByRating(@Param("role") UserRole role);

    User findByEmail(String email);

    @Query(value = "SELECT u FROM User u WHERE u.userRole = :role AND u.approved=true")
    List<User> findByUserRoleAndApproved(@Param("role") UserRole role);
}
