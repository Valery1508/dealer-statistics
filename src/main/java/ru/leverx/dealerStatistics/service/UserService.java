package ru.leverx.dealerStatistics.service;

import org.springframework.stereotype.Service;
import ru.leverx.dealerStatistics.dto.UserResponseDto;
import ru.leverx.dealerStatistics.dto.UserTopResponseDto;
import ru.leverx.dealerStatistics.entity.User;
import ru.leverx.dealerStatistics.entity.UserRole;

import java.util.List;

@Service
public interface UserService {

    UserResponseDto get(Long id);

    List<UserResponseDto> getUsersByRole(UserRole role);

    UserResponseDto approve(Long id);

    List<UserTopResponseDto> getTopOfTreiders();

    List<UserResponseDto> getUsersByRoleAndApproved(UserRole role);

    void calculateUserRating(User user);
}
