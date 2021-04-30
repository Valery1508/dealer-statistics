package ru.leverx.dealerStatistics.service;

import org.springframework.stereotype.Service;
import ru.leverx.dealerStatistics.dto.UserDto;
import ru.leverx.dealerStatistics.dto.UserResponseDto;
import ru.leverx.dealerStatistics.dto.UserTopResponseDto;
import ru.leverx.dealerStatistics.entity.UserRole;

import java.util.List;

@Service
public interface UserService {
    UserResponseDto create(UserDto userDto);

    UserResponseDto get(Long id);

    List<UserResponseDto> getUsersByRole(UserRole role);

    UserResponseDto approve(Long id);

    List<UserTopResponseDto> getTopOfTreiders();
}
