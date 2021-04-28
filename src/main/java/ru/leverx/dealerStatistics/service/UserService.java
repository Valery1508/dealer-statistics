package ru.leverx.dealerStatistics.service;

import org.springframework.stereotype.Service;
import ru.leverx.dealerStatistics.dto.UserDto;

@Service
public interface UserService {
    UserDto create(UserDto userDto);
    UserDto get(Long id);
}
