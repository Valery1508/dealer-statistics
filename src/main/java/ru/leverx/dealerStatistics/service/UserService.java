package ru.leverx.dealerStatistics.service;

import org.springframework.stereotype.Service;
import ru.leverx.dealerStatistics.dto.UserDto;
import ru.leverx.dealerStatistics.dto.UserResponseDto;

@Service
public interface UserService {
    UserResponseDto create(UserDto userDto);

    UserResponseDto get(Long id);
}
